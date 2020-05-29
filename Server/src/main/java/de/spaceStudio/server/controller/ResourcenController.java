package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Ressource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ResourcenController {
    /**
     * Get all ressources from db
     * @return List of all ressources
     */
    @RequestMapping(value = "/resourcen", method = RequestMethod.GET)
    List<Ressource> getAllRessources();
    /**
     * Get one ressource by Id
     *
     * @param id of the ressource
     * @return the Ressource
     */
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    Ressource getRessource(@PathVariable Integer id);
    /**
     * Creates a new ressource from JSON ressource object
     * @param ressource the ressource to be created, which is serialised from the POST JSON
     * @return the serialised Ressource
     */
    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    String addRessource(@RequestBody Ressource ressource);
    /**
     * Update data of the ressource
     * @param ressource the ressource to be updated, which is serialised from the POST JSON
     * @return the updated Ressource
     */
    @RequestMapping(value = "/resource", method = RequestMethod.PUT)
    Ressource updateRessource(@RequestBody Ressource ressource);
    /**
     * Delete ressource by Id
     * @param id of the ressource
     * @return JSON of the delted Ressource
     */
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.DELETE)
    String deleteRessourceById(@PathVariable Integer id);

    /**
     * Delete all ressources
     * @return JSON of deleted ressource
     */
    @RequestMapping(value = "/resourcen", method = RequestMethod.DELETE)
    String deleteAllRessources();

    String hashPassword(String weakPassword);
}
