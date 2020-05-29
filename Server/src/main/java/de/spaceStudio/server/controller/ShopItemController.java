package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.ShopItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ShopItemController {
    /**
     * Get all shopItems from db
     * @return List of all shopItems
     */
    @RequestMapping(value = "/shopItems", method = RequestMethod.GET)
    List<ShopItem> getAllShopItems();
    /**
     * Get one shopItem by Id
     *
     * @param id of the shopItem
     * @return the ShopItem
     */
    @RequestMapping(value = "/shopItem/{id}", method = RequestMethod.GET)
    ShopItem getShopItem(@PathVariable Integer id);

    /**
     * Creates a new shopItem from JSON shopItem object
     * @param shopItem the shopItem to be created, which is serialised from the POST JSON
     * @return the serialised ShopItem
     */
    @RequestMapping(value = "/shopItem", method = RequestMethod.POST)
    String addShopItem(@RequestBody ShopItem shopItem);
    /**
     * Update data of the shopItem
     * @param shopItem the shopItem to be updated, which is serialised from the POST JSON
     * @return the updated ShopItem
     */
    @RequestMapping(value = "/shopItem", method = RequestMethod.PUT)
    ShopItem updateShopItem(@RequestBody ShopItem shopItem);
    /**
     * Delete shopItem by Id
     * @param id of the shopItem
     * @return JSON of the delted ShopItem
     */
    @RequestMapping(value = "/shopItem/{id}", method = RequestMethod.DELETE)
    String deleteShopItemById(@PathVariable Integer id);
    /**
     * Delete all shopItems
     * @return JSON of deleted shopItem
     */
    @RequestMapping(value = "/shopItems", method = RequestMethod.DELETE)
    String deleteAllShopItems();

    String hashPassword(String weakPassword);
}
