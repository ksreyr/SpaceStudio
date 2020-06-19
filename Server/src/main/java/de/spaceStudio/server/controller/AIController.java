package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.AI;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface AIController {

    /**
     * Get all AIs from db
     *
     * @return List of all AIs
     */
    @RequestMapping(value = "/AIs", method = RequestMethod.GET)
    List<AI> getAllAIs();

    /**
     * Get one AI by Id
     *
     * @param id of the AI
     * @return the AI
     */
    @RequestMapping(value = "/AI/{id}", method = RequestMethod.GET)
    AI getAI(@PathVariable Integer id);

    /**
     * Creates a new AI from JSON AI object
     *
     * @param ai the AI to be created, which is serialised from the POST JSON
     * @return the serialised AI
     */
    @RequestMapping(value = "/AI", method = RequestMethod.POST)
    String addAI(@RequestBody AI ai);

    /**
     * Update data of the AI
     *
     * @param ai the AI to be updated, which is serialised from the POST JSON
     * @return the updated AI
     */
    @RequestMapping(value = "/AI", method = RequestMethod.PUT)
    AI updateAI(@RequestBody AI ai);

    /**
     * Delete AI by Id
     *
     * @param id of the AI
     * @return JSON of the delted AI
     */
    @RequestMapping(value = "/AI/{id}", method = RequestMethod.DELETE)
    String deleteAIById(@PathVariable Integer id);

    /**
     * Delete all AIs
     *
     * @return JSON of deleted AI
     */
    @RequestMapping(value = "/AIs", method = RequestMethod.DELETE)
    String deleteAllAIs();
}
