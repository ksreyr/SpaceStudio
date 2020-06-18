package de.bremen.server.controller;

import de.bremen.server.model.Section;
import de.bremen.server.model.Ship;
import de.bremen.server.model.Weapon;
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
     * @return JSON of the delted Ship
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
     * Check the validity of the Changes before updating the Server
     *
     * @param ship which will be checked
     * @return if the changes will be made
     */
    @RequestMapping(value = "/ship/{id}/validate", method = RequestMethod.GET)
    boolean validdateShip(@RequestBody Ship ship);

    /**
     * Find the shortest Path between two Sections on one Ship
     *
     * @param s     is the Ship
     * @param start is the starting section
     * @param end   is the ending section
     * @return A List of Sections which need to be passed
     */
    List<Section> findPath(Ship s, Section start, Section end);

    /**
     * Decide what the Crew will be doing
     *
     * @param s on this Ship
     */
    void applyCrewSkills(Ship s);


    /**
     * Recived the Value of the new Ship and persists it to the
     * Database if valid
     *
     * @param s is the attacked Ship
     */
    void applyAttack(Ship s);

    /**
     * Start an Attack
     *
     * @param w        is the Weapon
     * @param s        is the section which should be attacked
     * @param attacker the attacking Ship
     * @param defender the defending Ship
     */
    void startAttack(Weapon w, Section s, Ship attacker, Ship defender);

}
