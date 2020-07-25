package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.ShopRessource;
import de.spaceStudio.server.model.StopAbstract;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ShopRessourceController {
    /**
     * Get all ships from db
     *
     * @return List of all Ships
     */
    @RequestMapping(value = "/shopressources", method = RequestMethod.GET)
    List<ShopRessource> getAllShopRessources();

    /**
     * Get one ship by Id
     *
     * @param id of the ship
     * @return the Ship
     */
    @RequestMapping(value = "/shopressource/{id}", method = RequestMethod.GET)
    ShopRessource getShopRessource(@PathVariable Integer id);

    /**
     * Creates a new ship from JSON ship object
     *
     * @param shopRessource the ship to be created, which is serialised from the POST JSON
     * @return the serialised Ship
     */
    @RequestMapping(value = "/shopressource", method = RequestMethod.POST)
    String addShopRessource(@RequestBody ShopRessource shopRessource);

    /**
     * Update data of the ship
     * Later this needs to trigger the sync of clients which use this Ship
     *
     * @param shopRessource the ship to be updated, which is serialised from the POST JSON
     * @return the updated Ship
     */
    @RequestMapping(value = "/shopressource", method = RequestMethod.PUT)
    ShopRessource updateShopRessource(@RequestBody ShopRessource shopRessource);

    /**
     * Delete shop by Id
     *
     * @param id of the shop
     * @return JSON of the delted Ship
     */
    @RequestMapping(value = "/shopressource/{id}", method = RequestMethod.DELETE)
    String deleteShopRessourceById(@PathVariable Integer id);

    /**
     * Delete all players
     *
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/shopressource", method = RequestMethod.DELETE)
    String deleteAllShopRessources();

    /**
     * Delete all players
     *
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/getshopressourcebystop", method = RequestMethod.POST)
    String getShopRessourceByStop(@RequestBody StopAbstract stopAbstract);
    /**
     * Delete all players
     *
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/buyitem", method = RequestMethod.POST)
    String buyItem(@RequestBody List<ShopRessource> ressourceList);
}
