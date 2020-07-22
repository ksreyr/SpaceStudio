package de.spaceStudio.server.controller;

import com.google.gson.Gson;
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
    ActorRepository actorRepository;

    @Autowired
    CrewMemberRepository crewMemberRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    ShipRessourceRepository shipRessourceRepository;

    @Autowired
    ShopRessourceRepository shopRessourceRepository;
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

        Optional<Player> fetchPlayer = (playerRepository.findByName(player.getName()));
        if (fetchPlayer != null && fetchPlayer.isPresent()) {
            return "Name already registered, try another one :)";
        } else {
            Player savedPlayer = playerRepository.save(player);
            return HttpStatus.CREATED.toString();
        }
    }

    /**
     * Update data
     */
    @Override
    @RequestMapping(value = "/player", method = RequestMethod.PUT)
    public Player updatePlayer(@RequestBody Player player) {
        Player updatedPlayer = playerRepository.save(player);
        return updatedPlayer;
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
    public Set<String> getMutiplayers(){
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

        boolean fileClosed = JSONFile.cleanJSONSinglePlayerGame(player1.getSavedGame());
        if (fileClosed) {
            LOG.info("saved game success cleaned!");
            player1.setSavedGame(null);
            playerRepository.save(player1);
        }

        if (shipRepository.findByOwner(player1).isPresent()) {
            Ship ship = shipRepository.findByOwner(player1).get();
            StopAbstract stopAbstract = stopAbstractRepository.findByShips(ship).get();
            Universe universe = universeRepository.findByName(stopAbstract.getUniverse().getName()).get();
            List<StopAbstract> stopAbstracts = stopAbstractRepository.findByUniverse(universe).get();
            //sucht all the stations
            for (StopAbstract sa :
                    stopAbstracts) {
                List<Ship> shipList = sa.getShips();
                //sucht all the ship in the station
                for (Ship s :
                        shipList) {
                    if (sectionRepository.findAllByShip(s).isPresent()) {
                        List<Section> sections = sectionRepository.findAllByShip(s).get();
                        for (Section section :
                                sections) {
                            if (crewMemberRepository.findAllByCurrentSection(section).isPresent()) {
                                ArrayList<CrewMember> crewMemberList = crewMemberRepository
                                        .findAllByCurrentSection(section).get();
                                for (CrewMember c :
                                        crewMemberList) {
                                    crewMemberRepository.delete(c);
                                }
                            } else {
                                System.out.println("not CrewMember to erase");
                            }
                            if(weaponRepository.findBySection(section).isPresent()){
                                List<Weapon> weapons=weaponRepository.findBySection(section).get();
                                for (Weapon w :
                                        weapons) {
                                    weaponRepository.delete(w);
                                }
                            }else {
                                System.out.println("not Weapon to erase");
                            }
                            sectionRepository.delete(section);
                        }
                    } else {
                        System.out.println("not Sections to erase");
                    }

                    if (aiRepository.findByName(s.getOwner().getName()).isPresent()) {
                        AI ai = aiRepository.findByName(s.getOwner().getName()).get();
                        aiRepository.delete(ai);
                    }
                    if(stopAbstractRepository.findById(sa.getId()).isPresent()){
                    stopAbstractRepository.delete(sa);
                    }
                    if(shipRessourceRepository.findByShip(s).isPresent()){
                        shipRessourceRepository.delete(shipRessourceRepository.findByShip(s).get());
                    }
                    shipRepository.delete(s);
                }
            }
            for (StopAbstract s :
                    stopAbstracts) {
                    try {
                        ShopRessource shopRessource=shopRessourceRepository.findByStation((Station) s).get();
                        shopRessourceRepository.delete(shopRessource);
                    }catch (Exception e){

                    }
                    stopAbstractRepository.delete(s);

            }
            universeRepository.delete(universe);

        }
        return "DONE";
    }
}
