package de.spaceStudio.server.controller;


import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.SectionRepository;
import de.spaceStudio.server.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShipControllerImpl implements ShipController{
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
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
        Optional<Player> player= playerRepository.findByName( ship.getOwner().getName());
        ship.setOwner(player.get());
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
}
