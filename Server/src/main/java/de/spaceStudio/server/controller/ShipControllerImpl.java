package de.spaceStudio.server.controller;


import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.server.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
public class ShipControllerImpl implements ShipController{
    @Autowired
    ShipRepository shipRepository;

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
        shipRepository.save(ship);
        return HttpStatus.CREATED.toString();
    }

    @Override
    public Ship updateShip(Ship ship) {

        shipRepository.findById(ship.getId());
        return null;
    }

    @Override
    public String deleteShipById(Integer id) {
        return null;
    }

    @Override
    public String deleteAllShips() {
        return null;
    }

    @Override
    public boolean validdateShip(Ship ship) {
        return false;
    }

    @Override
    public List<Section> findPath(Ship s, Section start, Section end) {
        return null;
    }

    @Override
    public void applyCrewSkills(Ship s) {

    }

    @Override
    public void applyAttack(Ship s) {

    }

    @Override
    public void startAttack(Weapon w, Section s, Ship attacker, Ship defender) {

    }
}
