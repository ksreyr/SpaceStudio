package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import de.spaceStudio.server.utils.Global;
import de.spaceStudio.server.utils.JSONFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Miguel Caceres 09.05.2020
 */
@RestController
public class PlayerControllerImpl implements PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerControllerImpl.class);
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    GameController gameController;
    @Autowired
    CrewMemberRepository crewMemberRepository;
    @Autowired
    WeaponRepository weaponRepository;
    @Autowired
    ShipRessourceRepository shipRessourceRepository;
    @Autowired
    ShopRessourceRepository shopRessourceRepository;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    ActorStateRepository actorStateRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private UniverseRepository universeRepository;
    @Autowired
    private StopAbstractRepository stopAbstractRepository;
    @Autowired
    private AIRepository aiRepository;

    @Autowired
    ActorController actorController;

    @Autowired
    GameRoundRepository gameRoundRepository;

    @Autowired
    CombatRoundRepository combatRoundRepository;

    /**
     * This function is temporal in use to test client to Server connection
     * Login user if exists
     *
     * @param player which should be loged in
     * @return true if exists else false
     */
    @Override
    @RequestMapping(value = "/player/login", method = RequestMethod.POST)
    public Player loginUser(@RequestBody Player player) {
        Optional<Player> fetchPlayer = playerRepository.findByName(player.getName());
        if (fetchPlayer.isPresent() && authUser(fetchPlayer, player)) {
            Global.userLogged.add(player.getName());
            return fetchPlayer.get();
        }
        return null;
    }

    /**
     * Get all players from db
     */
    @Override
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Get one player by Id
     *
     * @param id of the player
     */
    @Override
    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public Player getPlayer(@PathVariable Integer id) {
        return playerRepository.findById(id).get();
    }

    /**
     * Creates a new player from JSON player object
     */
    @Override
    @RequestMapping(value = "/player", method = RequestMethod.POST)
    public String addPlayer(@RequestBody Player player) {
        // For the future hash password
        // player.setPassword(hashPassword(player.getPassword()));

        Optional<Player> fetchPlayer = playerRepository.findByName(player.getName());
        if (fetchPlayer.isPresent()) {
            return "Name already registered, try another one :)";
        } else {
            if (player.getState() == null) {
                ActorState as = new ActorState();
                as = actorStateRepository.save(as);
                player.setState(as);
            }
            playerRepository.save(player);
            return HttpStatus.CREATED.toString();
        }
    }

    /**
     * Update data
     */
    @Override
    @RequestMapping(value = "/player", method = RequestMethod.PUT)
    public Player updatePlayer(@RequestBody Player player) {
        Optional<Player> fetchPlayer = playerRepository.findById(player.getId());
        if (fetchPlayer.isPresent()) {
            gameController.setFightState(player);
            return playerRepository.save(player);
        } else
            throw new IllegalStateException(String.format("The Player %s to update does not exist", player.getName()));
    }

    /**
     * Delete player by Id
     */
    @Override
    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
    public String deletePlayerById(@PathVariable Integer id) {
        playerRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    /**
     * Delete all players
     */
    @Override
    @RequestMapping(value = "/players", method = RequestMethod.DELETE)
    public String deleteAllPlayers() {
        playerRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    /**
     * Get all logged players name
     *
     * @return
     */
    @Override
    @RequestMapping(value = "/player/logged-players", method = RequestMethod.GET)
    public Set<String> getLoggedPlayers() {
        return Global.userLogged;
    }

    @RequestMapping(value = "/player/multiplayer-list", method = RequestMethod.GET)
    public Set<String> getMutiplayers() {
        return Global.usersMultiPlayer;
    }

    /**
     * This function is temporal in use to logout user from game
     *
     * @return
     */
    @Override
    @RequestMapping(value = "/player/logout", method = RequestMethod.POST)
    public void logoutUser(@RequestBody Player player) {
        Global.userLogged.remove(player.getName());
    }

    /**
     * This function is temporal in use to logout user from multiplayer game
     *
     * @return
     */
    @RequestMapping(value = "/player/multiplayer/logout", method = RequestMethod.POST)
    public void logoutMultiPlayer(@RequestBody Player player) {
        Global.usersMultiPlayer.remove(player.getName());
        LOG.info("User :" + player.getName() + " removed from multiplayer");
    }


    /**
     * Salt the password
     */
    @Override
    public String hashPassword(String weakPassword) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(weakPassword.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * validate player
     *
     * @param fetchPlayer
     * @param player
     * @return auth user
     */
    public boolean authUser(Optional<Player> fetchPlayer, Player player) {
        return ((fetchPlayer.get().getName().equals(player.getName())
                && fetchPlayer.get().getPassword().equals(player.getPassword())));

    }


    @Override
    public String clean(Player player) {
        Player player1 = playerRepository.findByName(player.getName()).get();

        List<Ship> ohnePlanet=shipRepository.findAll();
        for (Ship sipohne:
             ohnePlanet) {
            if(stopAbstractRepository.findByShips(sipohne).isEmpty()){
                if(sectionRepository.findAllByShip(sipohne).isPresent()){
                    for (Section s :
                            sectionRepository.findAllByShip(sipohne).get()) {
                        if(weaponRepository.findBySection(s).isPresent()){
                            for (Weapon w :
                                    weaponRepository.findBySection(s).get()) {
                                weaponRepository.delete(w);
                            }
                        }
                        if(crewMemberRepository.findByCurrentSection(s).isPresent()){
                            crewMemberRepository.delete(crewMemberRepository.findByCurrentSection(s).get());
                        }
                        sectionRepository.delete(s);
                    }
                    shipRepository.delete(sipohne);
                }
            }
        }
        if (player.getState() != null) {
            player.setState(null);
            playerRepository.save(player);
            actorStateRepository.delete(player1.getState());
        }


        boolean fileClosed = JSONFile.cleanJSONSinglePlayerGame(player1.getSavedGame());
        if (fileClosed) {
            LOG.info("saved game success cleaned!");
            player1.setSavedGame(null);
            playerRepository.save(player1);
        }

        List<Ship> ships = shipRepository.findAllByOwner(player1);
        Set<Section> sections = new HashSet<>();
        List<Weapon> weapons = new ArrayList<>();
        List<CrewMember> crewMembers = new ArrayList<>();
        List<StopAbstract> stopAbstracts = new ArrayList<>();
       /* for (Ship s :
                ships) {
            Optional<List<Section>> secsFound = sectionRepository.findAllByShip(s);
            secsFound.ifPresent(sections::addAll);
            for (Section sec :
                    sections) {
                Optional<List<Weapon>> bySection = weaponRepository.findBySection(sec);
                bySection.ifPresent(weapons::addAll);
                Optional<List<CrewMember>> crewMembers1 = crewMemberRepository.findAllByCurrentSection(sec);
                crewMembers1.ifPresent(crewMembers::addAll);
            }
            crewMemberRepository.deleteAll(crewMembers);
            weaponRepository.deleteAll(weapons);

            List<Weapon> weaponList = new ArrayList<>();
            for (Section sectionWhereObjectiv:
                    sections) {
                if(weaponRepository.findAllByObjectiv(sectionWhereObjectiv).isPresent()){
                    for (Weapon w :
                            weaponRepository.findAllByObjectiv(sectionWhereObjectiv).get()) {
                        w.setObjectiv(null);
                        weaponRepository.save(w);
                    }
                }
            }
            sectionRepository.deleteAll(sections);
            Optional<List<ShipRessource>> shipRessourcesList = shipRessourceRepository.findByShip(s);
            shipRessourcesList.ifPresent(shipRessources -> shipRessourceRepository.deleteAll(shipRessources));
            Optional<StopAbstract> stopAbstracts1 = stopAbstractRepository.findByShips(s);
            stopAbstracts1.ifPresent(stopAbstracts::add);
        }
        stopAbstractRepository.deleteAll(stopAbstracts);
        if(ships.size()>1){
            shipRepository.delete(ships.get(0));
        }*/


        Optional<Ship> byOwner = Optional.empty();
        try {
            byOwner = shipRepository.findByOwner(player1);
        } catch (Exception e) {
            List<Ship> shipsDouble = shipRepository.findAllByOwner(player1);
            int id = Integer.MAX_VALUE;
            for (Ship s :
                    shipsDouble) {
                if (s.getId() < id) {
                    id = s.getId();
                }
            }
            Optional<List<Section>> sections1= sectionRepository.findAllByShip(shipRepository.findById(id).get());
            if (sections1.isPresent()) {
                for (Section s :
                        sections1.get()) {
                    s.setShip(null);
                    sectionRepository.save(s);
                    if(weaponRepository.findBySection(s).isPresent()){
                    for (Weapon w:
                            weaponRepository.findBySection(s).get()) {
                        try {
                            weaponRepository.delete(w);
                        }catch (Exception we){

                        }

                    }
                    }
                    if(crewMemberRepository.findByCurrentSection(s).isPresent()){
                            try {
                                crewMemberRepository.delete(crewMemberRepository.findByCurrentSection(s).get());
                            }catch (Exception cr){

                            }

                    }
                    sectionRepository.delete(s);
                }
            }
            shipRepository.deleteById(id);
        }
        if (byOwner.isPresent()) {
            Ship ship = new Ship();
            try {
                ship = shipRepository.findByOwner(player1).get();
            } catch (Exception e) {
                List<Ship> shipList = shipRepository.findAllByOwner(player1);
                shipRepository.delete(shipList.get(0));
                ship = shipList.get(0);
            }
            Optional<StopAbstract> stopAbstract = stopAbstractRepository.findByShips(ship);

            if (stopAbstract.isPresent() && stopAbstract.get().getUniverse() != null) {
                Optional<Universe> universe = universeRepository.findByName(stopAbstract.get().getUniverse().getName());

                if (universe.isPresent()) {
                    List<Actor> allByUniverse = actorController.findAllByUniverse(universe.get());
                    if (!allByUniverse.isEmpty()) {
                        for (Actor a : allByUniverse) {
                            List<GameRound> allByActor = gameRoundRepository.findAllByActor(a);
                            if (!allByActor.isEmpty()) {
                                for (GameRound g :
                                        allByActor) {
                                    gameRoundRepository.delete(g);
                                }
                            }

                        }
                    }


                    stopAbstracts = stopAbstractRepository.findByUniverse(universe.get());
                    Optional<StopAbstract> p9 = stopAbstracts.stream().filter(p -> p.getName().equals("p9")).findFirst();
                    if (p9.isPresent()) {
                        p9.get().setUniverse(null);
                        stopAbstractRepository.save(p9.get());
                    }
                    stopAbstracts.removeIf(s -> s.getName().equals("p9"));
                    //sucht all the stations
                    for (StopAbstract sa :
                            stopAbstracts) {
                        List<Ship> shipList = sa.getShips();
                        //sucht all the ship in the station
                        for (Ship s :
                                shipList) {

                            if (sectionRepository.findAllByShip(s).isPresent()) {
                                List<Section> sections1 = sectionRepository.findAllByShip(s).get();
                                for (Section section :
                                        sections1) {
                                    if (crewMemberRepository.findAllByCurrentSection(section).isPresent()) {
                                        List<CrewMember> crewMemberList = crewMemberRepository
                                                .findAllByCurrentSection(section).get();
                                        for (CrewMember c :
                                                crewMemberList) {
//
                                            crewMemberRepository.delete(c);
                                        }
                                    } else {
                                        System.out.println("not CrewMember to erase");
                                    }
                                    if (weaponRepository.findBySection(section).isPresent()) {
                                        List<Weapon> weapons1 = weaponRepository.findBySection(section).get();
                                        for (Weapon w :
                                                weapons1) {
                                            if (combatRoundRepository.findByWeaponsWhichHaveAttacked(w).isPresent()) {
                                                List<CombatRound> combatRoundList = combatRoundRepository.findByWeaponsWhichHaveAttacked(w).get();
                                                for (CombatRound cr :
                                                        combatRoundList) {
                                                    if (gameRoundRepository.findAllByCombatRounds(cr).isPresent()) {
                                                        List<GameRound> gameRoundList = gameRoundRepository.findAllByCombatRounds(cr).get();
                                                        for (GameRound gr :
                                                                gameRoundList) {
                                                            gameRoundRepository.delete(gr);
                                                        }
                                                    }
                                                    cr.setWeaponsWhichHaveAttacked(new ArrayList<>());
                                                    combatRoundRepository.save(cr);
                                                    combatRoundRepository.delete(cr);
                                                }
                                            }
                                            weaponRepository.delete(w);
                                        }
                                    } else {
                                        System.out.println("not Weapon to erase");
                                    }
                                    if (weaponRepository.findAllByObjectiv(section).isPresent()) {
                                        List<Weapon> weaponsObjetiv = weaponRepository.findAllByObjectiv(section).get();
                                        for (Weapon w :
                                                weaponsObjetiv) {
                                            w.setObjectiv(null);
                                            weaponRepository.save(w);
                                        }
                                    }
                                    sectionRepository.delete(section);
                                }
                            } else {
                                System.out.println("not Sections to erase");
                            }

                            if (aiRepository.findByName(s.getOwner().getName()).isPresent()) {
                                AI ai = aiRepository.findByName(s.getOwner().getName()).get();
                                for (GameRound g :
                                        gameRoundRepository.findAllByActor(ai)) {
                                    for (CombatRound cr :
                                            g.getCombatRounds()) {
                                        if (combatRoundRepository.findById(cr.getId()).isPresent()) {
                                            if (gameRoundRepository.findAllByCombatRounds(combatRoundRepository.findById(cr.getId()).get()).isPresent()) {
                                                List<GameRound> gameRoundList = gameRoundRepository.findAllByCombatRounds(combatRoundRepository.findById(cr.getId()).get()).get();
                                                for (GameRound gr :
                                                        gameRoundList) {
                                                    combatRoundRepository.deleteAll(gr.getCombatRounds());
                                                    gr.setCombatRounds(new ArrayList<>());
                                                    gameRoundRepository.save(gr);
                                                }
                                            }
                                        }
                                    }

                                    combatRoundRepository.deleteAll(g.getCombatRounds());
                                    gameRoundRepository.delete(g);
                                }
                                if (ai.getState() != null) {
                                    ActorState toDelete = ai.getState();
                                    aiRepository.delete(ai);
                                    actorStateRepository.delete(toDelete);
                                    ai.setState(null);
                                } else {
                                    aiRepository.delete(ai);
                                }
                            }
                            if (stopAbstractRepository.findById(sa.getId()).isPresent()) {
                                if (stationRepository.existsById(sa.getId())) {
                                    Optional<Station> station = stationRepository.findById(sa.getId());
                                    if (station.isPresent()) {
                                        if (shopRessourceRepository.findByStation(station.get()).isPresent()) {
                                            List<ShopRessource> shopRessources = shopRessourceRepository.findByStation(station.get()).get();
                                            for (ShopRessource sr :
                                                    shopRessources) {
                                                shopRessourceRepository.delete(sr);
                                            }
                                        }
                                    }
                                }
                                stopAbstractRepository.delete(sa);

                            }
                            if (shipRessourceRepository.findByShip(s).isPresent()) {
                                List<ShipRessource> shipRessources = shipRessourceRepository.findByShip(s).get();
                                for (ShipRessource sr :
                                        shipRessources) {
                                    shipRessourceRepository.delete(sr);
                                }
                            }
                            try {
                                shipRepository.delete(s);
                            } catch (Exception e) {
                                s.setOwner(null);
                                shipRepository.save(s);
                                shipRepository.delete(s);
                            }
                        }
                    }
                    stopAbstracts = stopAbstractRepository.findByUniverse(universe.get());
                    p9 = stopAbstracts.stream().filter(p -> p.getName().equals("p9")).findFirst();
                    if (p9.isPresent()) {
                        p9.get().setUniverse(null);
                        stopAbstractRepository.save(p9.get());
                    }
                    stopAbstracts.removeIf(s -> s.getName().equals("p9"));
                    if (!stopAbstracts.isEmpty()) {
                        for (StopAbstract s :
                                stopAbstracts) {

                            try {

                                if (shopRessourceRepository.findByStation(s).isPresent()) {
                                    Optional<List<ShopRessource>> shopRessources = shopRessourceRepository.findByStation(s);
                                    if (shopRessources.isPresent()) {
                                        for (ShopRessource sr :
                                                shopRessources.get()) {
                                            shopRessourceRepository.delete(sr);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error("Cleaning has crashed" + e.getLocalizedMessage());
                            }
                            stopAbstractRepository.delete(s);
                        }
                    }
                    universeRepository.delete(universe.get());
                }
            }
        }
        return "DONE";
    }

    @Override
    public List<GameRound> getGameRoundsByPlayer(Integer id) {
        Optional<Actor> a = actorRepository.findById(id);
        if (a.isPresent()) {
            return gameRoundRepository.findAllByActor(a.get());
        } else throw new IllegalArgumentException(String.format("Player %s does not exist", id));
    }
}
