package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.ActorState;
import de.spaceStudio.server.handler.MultiPlayerGame;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.ActorStateRepository;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
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
    @Autowired
    StopAbstractRepository stopAbstractRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ActorStateRepository actorStateRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StopAbstractControllerImpl.class);

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
        StopAbstract stopAbstractToUpdate = stopAbstractRepository.save(stopAbstract);
        return stopAbstractToUpdate;
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
//        Optional<Player> p = playerRepository.findById(stopStart.getShips().stream().filter(s -> s.getOwner()
//                .getClass().getName().equals("Player")).findFirst().get().getId());
//                // FIXME TERRIBLE BUG. No one knows who wants to jump, if 2 players at stop

        Optional<Actor> p =  Optional.of(stopEnd.getShips().get(0).getOwner());
        if (stopEnd.getShips().size() > 1) {
            return HttpStatus.EXPECTATION_FAILED.toString();
        }
        Optional<Ship> ship = Optional.of(stopStart.getShips().get(0));
        ArrayList<Ship> ships = (ArrayList<Ship>) stopStart.getShips();
        for (Ship s :
                ships) {
            if (s.getOwner() != null) {
                ship = Optional.of(s);
                for (MultiPlayerGame xs :
                        Global.MultiPlayerGameSessions.values()) {
                    if (xs.players.contains(p)) {

                        if (p.isPresent()) {
                            ActorState state = p.get().getState();
                            xs.players.remove(p.get());
                            state.setStopState(StopState.JUMPING);
                            actorStateRepository.save(state);
                            xs.players.add(p.get());
                            LOGGER.info(String.format("Player %s is ready", p.get().getId()));
                        }
                    }
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
    public Boolean canLand(Player player) {
        boolean canLand = false;  // Assume no one is jumping
        for (MultiPlayerGame multiPlayerGame :
                Global.MultiPlayerGameSessions.values()) {

            // Suche nach dem aktuellen Spieler
            List<Actor> playerSet = multiPlayerGame.players;

            // Gucke für jeden Spieler aus dem Spiel
            for (int i = 0; i < multiPlayerGame.players.size(); i++) {
                Actor p = playerSet.get(i);

                if (p.equals(player)) {  // Dies ist das Spiel des Spielers

                    boolean needsToWait = false;
                    for (Actor p1 :
                        // Gucke für jeden Boolean Wert aus dem Game
                            multiPlayerGame.players) {
                        if (!p1.getState().getStopState().equals(StopState.JUMPING)) {  // Falls jemand am Springen ist
                            needsToWait = true;
                            break;
                        }
                    }
                    canLand = !needsToWait;
                }

                List<Actor> landing = multiPlayerGame.players.stream().filter(u -> u.getState().getStopState().equals(StopState.JUMPING)).collect(Collectors.toList());
               if (canLand && landing.size() == 2) {
                   for (Actor actor:
                        multiPlayerGame.players) {
                       actor.getState().setStopState(StopState.EXPLORING);
                       actorStateRepository.save(actor.getState());
                       LOGGER.info(String.format("Player %s can now Land", player.getId()));
                   }
               }

            }
        }
        return canLand; // If there are Still Players who are Jumping
    }

//    @Override
//    public String hasLanded(Player player) {
//        try {
//
//            for (MultiPlayerGame xs :
//                    Global.MultiPlayerGameSessions.values()) {
//                if (xs.players.contains(player)) {
//                    Optional<Player> p = playerRepository.findById(player.getId());
//                    if (p.isPresent()) {
//                        xs.players.remove(p.get());
//                        ActorState state = p.get().getState();
//                        state.setStopState(StopState.EXPLORING);
//                        xs.players.add(player);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("Player has not been Found");
//            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
//        }
//        return HttpStatus.ACCEPTED.toString();
//    }
}