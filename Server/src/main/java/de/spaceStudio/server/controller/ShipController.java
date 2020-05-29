package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Ship;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ShipController {
    /**
     * Get all ships from db
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
     * @param ship the ship to be created, which is serialised from the POST JSON
     * @return the serialised Ship
     */
    @RequestMapping(value = "/ship", method = RequestMethod.POST)
    String addShip(@RequestBody Ship ship);

    /**
     * Update data of the ship
     * @param ship the ship to be updated, which is serialised from the POST JSON
     * @return the updated Ship
     */
    @RequestMapping(value = "/ship", method = RequestMethod.PUT)
    Ship updateShip(@RequestBody Ship ship);
    /**
     * Delete ship by Id
     * @param id of the ship
     * @return JSON of the delted Ship
     */
    @RequestMapping(value = "/ship/{id}", method = RequestMethod.DELETE)
    String deleteShipById(@PathVariable Integer id);

    /**
     * Delete all players
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/ships", method = RequestMethod.DELETE)
    String deleteAllShips();

    @RequestMapping(value = "/ship/{id}/validate", method = RequestMethod.GET)
    boolean  validDateShip(@RequestBody Ship ship);

    String hashPassword(String weakPassword);


}
