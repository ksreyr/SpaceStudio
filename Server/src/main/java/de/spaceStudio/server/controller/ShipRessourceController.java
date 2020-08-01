package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.ShipRessource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ShipRessourceController {

    /**
     * Get all ships from db
     *
     * @return List of all Ships
     */
    @RequestMapping(value = "/shipressources", method = RequestMethod.GET)
    List<ShipRessource> getAllShipRessources();

    /**
     * Get one ship by Id
     *
     * @param id of the ship
     * @return the Ship
     */
    @RequestMapping(value = "/shipressource/{id}", method = RequestMethod.GET)
    ShipRessource getShipRessource(@PathVariable Integer id);

    /**
     * Creates a new ship from JSON ship object
     *
     * @param shipRessource the ship to be created, which is serialised from the POST JSON
     * @return the serialised Ship
     */
    @RequestMapping(value = "/shipressource", method = RequestMethod.POST)
    String addShipRessource(@RequestBody ShipRessource shipRessource);

    /**
     * Update data of the ship
     * Later this needs to trigger the sync of clients which use this Ship
     *
     * @param shipRessource the ship to be updated, which is serialised from the POST JSON
     * @return the updated Ship
     */
    @RequestMapping(value = "/shipressource", method = RequestMethod.PUT)
    ShipRessource updateShipRessource(@RequestBody ShipRessource shipRessource);

    /**
     * Delete ship by Id
     *
     * @param id of the ship
     * @return JSON of the delted Ship
     */
    @RequestMapping(value = "/shipressource/{id}", method = RequestMethod.DELETE)
    String deleteShipRessourceById(@PathVariable Integer id);

    /**
     * Delete all players
     *
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/shipressource", method = RequestMethod.DELETE)
    String deleteAllShipRessources();


    /**
     * Recieve a Ship with the new Power Distribution
     * Return the Ship from the client if changes are accepted
     * Other wise it return the ship as it is stored on the Server
     *
     * @param shipRessource the ship to be updated, which is serialised from the POST JSON
     * @return the updated Ship
     */
    @RequestMapping(value = "/shipressource/energy", method = RequestMethod.PUT)
    String updateEnergy(@RequestBody ShipRessource shipRessource);

    @RequestMapping(value = "/shipressourcebyship", method = RequestMethod.POST)
    String getResourcebyShip(@RequestBody Ship ship);

    @GetMapping(value =  "/buy/{id}/{amount}")
    Boolean buyFor(@PathVariable Integer id, @PathVariable Integer amount);

}
