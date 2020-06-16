package de.bremen.server.controller;

import de.bremen.server.model.ShopRessource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ShopItemController {

    /**
     * Get all shopItems from db
     *
     * @return List of all shopItems
     */
    @RequestMapping(value = "/shopItems", method = RequestMethod.GET)
    List<ShopRessource> getAllShopItems();

    /**
     * Get one shopItem by Id
     *
     * @param id of the shopItem
     * @return the ShopRessource
     */
    @RequestMapping(value = "/shopItem/{id}", method = RequestMethod.GET)
    ShopRessource getShopItem(@PathVariable Integer id);


    /**
     * Creates a new shopRessource from JSON shopRessource object
     *
     * @param shopRessource the shopRessource to be created, which is serialised from the POST JSON
     * @return the serialised ShopRessource
     */
    @RequestMapping(value = "/shopItem", method = RequestMethod.POST)
    String addShopItem(@RequestBody ShopRessource shopRessource);

    /**
     * Update data of the shopRessource
     *
     * @param shopRessource the shopRessource to be updated, which is serialised from the POST JSON
     * @return the updated ShopRessource
     */
    @RequestMapping(value = "/shopItem", method = RequestMethod.PUT)
    ShopRessource updateShopItem(@RequestBody ShopRessource shopRessource);

    /**
     * Delete shopItem by Id
     *
     * @param id of the shopItem
     * @return JSON of the delted ShopRessource
     */
    @RequestMapping(value = "/shopItem/{id}", method = RequestMethod.DELETE)
    String deleteShopItemById(@PathVariable Integer id);

    /**
     * Delete all shopItems
     *
     * @return JSON of deleted shopItem
     */
    @RequestMapping(value = "/shopItems", method = RequestMethod.DELETE)
    String deleteAllShopItems();

    String hashPassword(String weakPassword);
}
