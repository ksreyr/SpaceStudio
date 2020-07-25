package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.handler.MultiPlayerGame;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
import de.spaceStudio.server.utils.Global;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class StopAbstractControllerImpl implements StopAbstractController {
    @Autowired
    StopAbstractRepository stopAbstractRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;

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
        StopAbstract stopAbstractToUpdate= stopAbstractRepository.save(stopAbstract);
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
        Player player=new Player();
        Ship ship= new Ship();
        ArrayList<Ship> ships= (ArrayList<Ship>) stopStart.getShips();
        for (Ship s :
                ships) {
            if(s.getOwner()!=null){
                player= playerRepository.findByName(s.getOwner().getName()).get();
                ship=s;

                for (MultiPlayerGame xs :
                        Global.MultiPlayerGameSessions.values()) {
                    if (xs.players.containsKey(player)) {
                        xs.players.put(player, true);
                    }
                }
            }
        }

        ship= shipRepository.findShipByNameAndAndOwner(ship.getName(),player).get();
        stopStart= stopAbstractRepository.findByShips(ship).get();
        List<StopAbstract> stopAbstracts = new ArrayList<>();
        stopAbstracts= stopAbstractRepository.findByUniverse(stopStart.getUniverse()).get();
        for (StopAbstract s:
                stopAbstracts) {
            if(s.getName().equals(stopEnd.getName())){
                stopEnd=s;
            }

        }
        //Delete proces
        for (Ship s :
                ships) {
            if(ship.getName().equals(s.getName())){
                ships.remove(s);
                break;
            }
        }


        stopStart.setShips(ships);
        stopAbstractRepository.save(stopStart);
        List<Ship> shipJump= new ArrayList<>();
        shipJump= stopEnd.getShips();
        shipJump.add(ship);
        stopEnd.setShips(shipJump);
        stopAbstractRepository.save(stopEnd);
        Gson gson= new Gson();

        return gson.toJson(shipJump);
    }


    @Override
    public Boolean canLand(Player player) {
        boolean playersJumping = false;  // Assume no one is jumping
        for (MultiPlayerGame multiPlayerGame :
                Global.MultiPlayerGameSessions.values()) {

            // Suche nach dem aktuellen Spieler
            Player[] playerSet = multiPlayerGame.players.keySet().toArray(new Player[0]);

            // Gucke für jeden Spieler
            for (int i = 0; i < multiPlayerGame.players.size(); i++) {
                Player p = playerSet[i];

                if (p.equals(player)) {
                    for (Boolean b :
                        // Gucke für jeden Boolean Wert aus dem Game
                            multiPlayerGame.players.values()) {
                        if (b) {  // Falls jemand am Springen ist
                            playersJumping = true;
                        }
                    }
                }
            }
        }
        return !playersJumping; // If there are Still Players who are Jumping
    }

    @Override
    public String hasLanded(Player player) {
        try {

        for (MultiPlayerGame xs :
                Global.MultiPlayerGameSessions.values()) {
            if (xs.players.containsKey(player)) {
                xs.players.put(player, false);
            }
        }
        } catch (Exception e) {
            LOGGER.error("Player has not been Found");
            return HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
        return HttpStatus.ACCEPTED.toString();
    }
}