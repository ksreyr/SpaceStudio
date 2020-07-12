package de.spaceStudio.server.controller;


import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.ActorRepository;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.SectionRepository;
import de.spaceStudio.server.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ShipControllerImpl implements ShipController{
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    ActorRepository actorRepository;
    @Override
    @RequestMapping(value = "/ships",method = RequestMethod.GET)
    public List<Ship> getAllShips() {
        return shipRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/Ship/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable Integer id) {
        return shipRepository.findById(id).get();
    }

    @Override
    @RequestMapping(value = "/ship",method = RequestMethod.POST)
    public String addShip(@RequestBody Ship ship) {
        //Optional<Player> player= playerRepository.findByName( ship.getOwner().getName());
        Optional<Actor> actor= actorRepository.findByName( ship.getOwner().getName());
        Player player= new Player();
        AI ai= new AI();
        try{
            player= (Player) actor.get();
            ship.setOwner(player);
            shipRepository.save(ship);
            Ship shipid=shipRepository.findShipByNameAndAndOwner(ship.getName(),player).get();
            return shipid.getId().toString();
        }catch (Exception e){
             ai= (AI) actor.get();
             ship.setOwner(ai);
            shipRepository.save(ship);
            Ship shipid=shipRepository.findShipByNameAndAndOwner(ship.getName(),ai).get();
            return shipid.getId().toString();
        }
    }

    @RequestMapping(value = "/shipstoadd",method = RequestMethod.POST)
    public String addShip(@RequestBody List<Ship> ships) {
        //Optional<Player> player= playerRepository.findByName( ship.getOwner().getName());
        /*Optional<Actor> actor= actorRepository.findByName( ship.getOwner().getName());
        Player player= new Player();
        AI ai= new AI();
        try{
            player= (Player) actor.get();
            ship.setOwner(player);
            shipRepository.save(ship);
            Ship shipid=shipRepository.findShipByNameAndAndOwner(ship.getName(),player).get();
            return shipid.getId().toString();
        }catch (Exception e){
            ai= (AI) actor.get();
            ship.setOwner(ai);
            shipRepository.save(ship);
            Ship shipid=shipRepository.findShipByNameAndAndOwner(ship.getName(),ai).get();
            return shipid.getId().toString();
        }*/
        ArrayList<Ship> shipArrayList= new ArrayList<>();
        for (Ship s :
                ships) {
            Ship ship=shipRepository.save(s);
            shipArrayList.add(ship);
        }
        Gson gson= new Gson();
        gson.toJson(shipArrayList);
        return gson.toJson(shipArrayList);
    }
    @Override
    public Ship updateShip(Ship ship) {

        shipRepository.findById(ship.getId());
        return null;
    }

    @Override
    public String deleteShipById(Integer id) {
        shipRepository.delete(shipRepository.findById(id).get());
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    public String deleteAllShips() {
        shipRepository.deleteAll();
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    public String updateEnergy(Ship ship) {
        shipRepository.save(ship);
        if (checkEnergy(shipRepository.findById(ship.getId()).get(), ship) == true) {
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.FORBIDDEN.toString();
        }
    }

    /**
     * Start an Attack
     *
     * @param w        is the Weapon
     * @param s        is the section which should be attacked
     * @param attacker the attacking Ship
     * @param defender the defending Ship
     */
    @Override
    public Ship startAttack(Weapon w, Section s, Ship attacker, Ship defender) {
        return null;
    }

    @Autowired
    SectionRepository sectionRepository;

    @Override
    public boolean checkEnergy(Ship oldShip, Ship newShip) {
        int powerTotal = oldShip.getPower();
        // Add Check
        return true;
    }

    @Override
    public String shipNameValidation(Ship ship) {
        Player player=playerRepository.findByName(ship.getOwner().getName()).get();
        if(shipRepository.findShipByNameAndAndOwner(ship.getName(),player).isEmpty()){
            return "Avaible";
        }
        return HttpStatus.BAD_REQUEST.toString();
    }
}
