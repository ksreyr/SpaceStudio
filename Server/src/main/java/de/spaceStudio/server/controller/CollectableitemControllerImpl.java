package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.CollectableItem;
import de.spaceStudio.server.repository.CollectableItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class CollectableitemControllerImpl implements CollectableItemController{
    @Autowired
    private CollectableItemsRepository collectableItemsRepository;

    @Override
    @RequestMapping(value = "/collectableitems", method = RequestMethod.GET)
    public List<CollectableItem> getAllCollectableItems() {
        return collectableItemsRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/collectableitems/{id}", method = RequestMethod.GET)
    public CollectableItem getCollectableItem(@PathVariable Integer id) {
        return collectableItemsRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/collectableitem", method = RequestMethod.POST)
    public String addCollectableItem(@RequestBody CollectableItem collectableItems) {
        collectableItemsRepository.save(collectableItems);
        return collectableItems.toString();
    }

    @Override
    @RequestMapping(value = "/collectableitem", method = RequestMethod.PUT)
    public CollectableItem updateCollectableItem(@RequestBody CollectableItem collectableItems) {
        CollectableItem collectableItemToUpdate =
                collectableItemsRepository.findById(collectableItems.getId()).get();
        return collectableItemToUpdate;
    }

    @Override
    @RequestMapping(value = "/collectableitem/{id}", method = RequestMethod.DELETE)
    public String deleteCollectableItemById(@PathVariable Integer id) {
        collectableItemsRepository.deleteById(id);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/section", method = RequestMethod.DELETE)
    public String deleteAllCollectableItems() {
        collectableItemsRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public String hashPassword(String weakPassword) {
        return null;
    }
}
