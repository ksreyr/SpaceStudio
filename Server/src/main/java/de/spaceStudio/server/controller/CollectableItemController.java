package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.CollectableItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface CollectableItemController {
    /**
     * Get all collectableItemss from db
     * @return List of all collectableItemss
     */
    @RequestMapping(value = "/collectableItems", method = RequestMethod.GET)
    List<CollectableItem> getAllCollectableItems();
    /**
     * Get one collectableItems by Id
     *
     * @param id of the collectableItems
     * @return the CollectableItems
     */
    @RequestMapping(value = "/collectableItem/{id}", method = RequestMethod.GET)
    CollectableItem getCollectableItem(@PathVariable Integer id);
    /**
     * Creates a new collectableItems from JSON collectableItems object
     * @param collectableItems the collectableItems to be created, which is serialised from the POST JSON
     * @return the serialised CollectableItems
     */
    @RequestMapping(value = "/collectableItem", method = RequestMethod.POST)
    String addCollectableItem(@RequestBody CollectableItem collectableItems);
    /**
     * Update data of the collectableItems
     * @param collectableItems the collectableItems to be updated, which is serialised from the POST JSON
     * @return the updated CollectableItems
     */
    @RequestMapping(value = "/collectableItem", method = RequestMethod.PUT)
    CollectableItem updateCollectableItem(@RequestBody CollectableItem collectableItems);
    /**
     * Delete collectableItems by Id
     * @param id of the collectableItems
     * @return JSON of the delted CollectableItems
     */
    @RequestMapping(value = "/collectableItem/{id}", method = RequestMethod.DELETE)
    String deleteCollectableItemById(@PathVariable Integer id);

    /**
     * Delete all collectableItemss
     * @return JSON of deleted collectableItems
     */
    @RequestMapping(value = "/collectableItems", method = RequestMethod.DELETE)
    String deleteAllCollectableItems();

    String hashPassword(String weakPassword);
}
