package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.handler.MultiPlayerGame;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import de.spaceStudio.server.utils.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class StopAbstractControllerImpl implements StopAbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopAbstractControllerImpl.class);
    @Autowired
    StopAbstractRepository stopAbstractRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    GameRoundRepository gameRoundRepository;
    @Autowired
    ActorStateRepository actorStateRepository;

    @Override
    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.GET)
    public List<StopAbstract> getAllStopAbstracts() {
        return stopAbstractRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.GET)
    public StopAbstract getStopAbstract(@PathVariable Integer id) {
        return stopAbstractRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/stopAbstract", method = RequestMethod.POST)
    public String addStopAbstract(@RequestBody StopAbstract stopAbstract) {
        stopAbstractRepository.save(stopAbstract);
        return HttpStatus.CREATED.toString();
    }

    @Override
    @RequestMapping(value = "/stopAbstract", method = RequestMethod.PUT)
    public StopAbstract updateStopAbstract(@RequestBody StopAbstract stopAbstract) {
        return stopAbstractRepository.save(stopAbstract);
    }

    @Override
    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.DELETE)
    public String deleteStopAbstractById(@PathVariable Integer id) {
        stopAbstractRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.DELETE)
    public String deleteAllStopAbstracts() {
        stopAbstractRepository.deleteAll();
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    public String makeJump(@RequestBody List<StopAbstract> stops) {

        StopAbstract stopStart = stops.get(0);
        stops.remove(stopStart);

        StopAbstract stopEnd = stops.get(0);
        Optional<Actor> p = actorRepository.findById(stopEnd.getShips().get(0).getOwner().getId());

        if (stopEnd.getShips().size() > 1) {
            return HttpStatus.EXPECTATION_FAILED.toString();
        }
        Optional<Ship> ship = Optional.of(stopStart.getShips().get(0));
        ship.ifPresent(s -> s.setShield(s.getMaxShield()));  // Reset Shield to max Shield
        ArrayList<Ship> ships = (ArrayList<Ship>) stopStart.getShips();
        for (Ship s :
                ships) {
            if (s.getOwner() != null) {
                ship = Optional.of(s);

                if (p.isPresent()) {
                    ActorState state = p.get().getState();
                    state.setStopState(StopState.JUMPING);
                    actorStateRepository.save(state);
                    // Start a new Game Round for the Player
                    GameRound gameRound = new GameRound();
                    gameRound.setCurrentStop(stopEnd);
                    gameRound.setActor(p.get());
                    gameRoundRepository.save(gameRound);
                    LOGGER.info(String.format("Player %s is ready", p.get().getId()));
                }
            }
        }

        if (p.isPresent()) {
            ship = shipRepository.findShipByNameAndOwner(ship.get().getName(), p.get());
            if (ship.isPresent()) {
                stopStart = stopAbstractRepository.findByShips(ship.get()).get();

                List<StopAbstract> stopAbstracts = new ArrayList<>();
                stopAbstracts = stopAbstractRepository.findByUniverse(stopStart.getUniverse()).get();
                for (StopAbstract s :
                        stopAbstracts) {
                    if (s.getName().equals(stopEnd.getName())) {
                        stopEnd = s;
                    }

                }
                //Delete proces
                for (Ship s :
                        ships) {
                    if (ship.get().getName().equals(s.getName())) {
                        ships.remove(s);
                        break;
                    }
                }


                stopStart.setShips(ships);
                stopAbstractRepository.save(stopStart);
                List<Ship> shipJump = new ArrayList<>();
                shipJump = stopEnd.getShips();
                shipJump.add(ship.get());
                stopEnd.setShips(shipJump);
                stopAbstractRepository.save(stopEnd);


                Gson gson = new Gson();

                return gson.toJson(shipJump);
            }
        }

        Gson gson = new Gson();

        return gson.toJson(new ArrayList<Ship>());
    }
    @Override
    public StopAbstract getOnlinePlanet() {

        Optional<StopAbstract> p9 = stopAbstractRepository.findByName("p9");
        if(p9.isPresent()){
            System.out.println("FOUD");
        }
        return stopAbstractRepository.findByName("p9").orElseThrow(IllegalStateException::new);
    }



    @Override
    public Boolean canLand(Integer id, String session) {
        boolean canLand = false;  // Assume no one is jumping
        Optional<Player> player = playerRepository.findById(id);

        MultiPlayerGame multiPlayerGame = Global.MultiPlayerGameSessions.get(session);

        multiPlayerGame.setPlayers(actorRepository.findAllById(List.of(multiPlayerGame.getPlayerOne().getId(),
                multiPlayerGame.getPlayerTwo().getId())));

        // Suche nach dem aktuellen Spieler
        List<Actor> playerSet = Global.MultiPlayerGameSessions.get(session).getPlayers();

        if (player.isPresent() && !playerSet.isEmpty()) {

            // Gucke für jeden Spieler aus der Spiel Session ob er am Springe ist
            // Wenn nicht, dann muss der andere Warten
            boolean needsToWait = false;
            for (Actor p1 :
                // Gucke für jeden Boolean Wert aus dem Game
                    playerSet) {
                if (!p1.getState().getStopState().equals(StopState.JUMPING)) {  // Falls jemand am Springen ist
                    needsToWait = true;
                    break;
                }
            }
            canLand = !needsToWait;

            List<Actor> landing = playerSet.stream().filter(u -> !u.getState().getStopState().equals(StopState.JUMPING)).collect(Collectors.toList());
            if (canLand && landing.size() == 2) {
                for (Actor actor :
                        playerSet) {
                    actor.getState().setStopState(StopState.EXPLORING);
                    actorStateRepository.save(actor.getState());
                    LOGGER.info(String.format("Player %s can now Land", player.get().getId()));
                }
            }
        }
        return canLand; // If there are Still Players who are Jumping
    }

    @Override
    public String hasLanded(Player pPlayer) {
        try {
            Optional<Player> player = playerRepository.findById(pPlayer.getId());
            player.ifPresent(value -> value.getState().setStopState(StopState.EXPLORING));
            player.ifPresent(value -> playerRepository.save(value));

        } catch (Exception e) {
            LOGGER.error("Player has not been Found");
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
        return HttpStatus.ACCEPTED.toString();
    }
}
