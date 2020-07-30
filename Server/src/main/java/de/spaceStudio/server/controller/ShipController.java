package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ShipController {

    /**
     * Get all ships from db
     *
     * @return List of all Ships
     */
    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    List<Ship> getAllShips();

    /**
     * Get one ship by Id
     *
     * @param id of the ship
     * @return the Ship
     */
    @RequestMapping(value = "/ship/{id}", method = RequestMethod.GET)
    Ship getShip(@PathVariable Integer id);

    /**
     * Creates a new ship from JSON ship object
     *
     * @param ship the ship to be created, which is serialised from the POST JSON
     * @return the serialised Ship
     */
    @RequestMapping(value = "/ship", method = RequestMethod.POST)
    String addShip(@RequestBody Ship ship);

    /**
     * Update data of the ship
     * Later this needs to trigger the sync of clients which use this Ship
     *
     * @param ship the ship to be updated, which is serialised from the POST JSON
     * @return the updated Ship
     */
    @RequestMapping(value = "/ship", method = RequestMethod.PUT)
    Ship updateShip(@RequestBody Ship ship);

    /**
     * Delete ship by Id
     *
     * @param id of the ship
     * @return JSON of the deleted Ship
     */
    @RequestMapping(value = "/ship/{id}", method = RequestMethod.DELETE)
    String deleteShipById(@PathVariable Integer id);

    /**
     * Delete all players
     *
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/ships", method = RequestMethod.DELETE)
    String deleteAllShips();


    /**
     * Receive a Ship with the new Power Distribution
     * Return the Ship from the client if changes are accepted
     * Other wise it return the ship as it is stored on the Server
     *
     * @param ship the ship to be updated, which is serialised from the POST JSON
     * @return the updated Ship
     */
    @RequestMapping(value = "/ship/energy", method = RequestMethod.PUT)
    String updateEnergy(@RequestBody Ship ship);

    /**
     * Start an Attack
     *
     * @param w        is the Weapon
     * @param s        is the section which should be attacked
     * @param attacker the attacking Ship
     * @param defender the defending Ship
     */
    @RequestMapping(value = "/ship/attack", method = RequestMethod.GET)
    Ship startAttack(@RequestBody Weapon w, @RequestBody Section s, @RequestBody Ship attacker, @RequestBody Ship defender);

    boolean checkEnergy(Ship oldShip, Ship newShip);

    @RequestMapping(value = "/shipname", method = RequestMethod.POST)
    String shipNameValidation(@RequestBody Ship ship);


}
