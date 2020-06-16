package de.bremen.server.controller;

import de.bremen.server.model.Universe;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface UniverseController {

    /**
     * Get all universes from db
     *
     * @return List of all universes
     */
    @RequestMapping(value = "/universes", method = RequestMethod.GET)
    List<Universe> getAllUniverses();


    /**
     * Get one universe by Id
     *
     * @param id of the universe
     * @return the Universe
     */
    @RequestMapping(value = "/universe/{id}", method = RequestMethod.GET)
    Universe getUniverse(@PathVariable Integer id);


    /**
     * Creates a new universe from JSON universe object
     *
     * @param universe the universe to be created, which is serialised from the POST JSON
     * @return the serialised Universe
     */
    @RequestMapping(value = "/universe", method = RequestMethod.POST)
    String addUniverse(@RequestBody Universe universe);


    /**
     * Update data of the universe
     *
     * @param universe the universe to be updated, which is serialised from the POST JSON
     * @return the updated Universe
     */
    @RequestMapping(value = "/universe", method = RequestMethod.PUT)
    Universe updateUniverse(@RequestBody Universe universe);


    /**
     * Delete universe by Id
     *
     * @param id of the universe
     * @return JSON of the delted Universe
     */
    @RequestMapping(value = "/universe/{id}", method = RequestMethod.DELETE)
    String deleteUniverseById(@PathVariable Integer id);


    /**
     * Delete all universes
     *
     * @return JSON of deleted universe
     */
    @RequestMapping(value = "/universes", method = RequestMethod.DELETE)
    String deleteAllUniverses();

    String hashPassword(String weakPassword);
}

