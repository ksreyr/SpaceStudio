package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Planet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface PlanetController {

    /**
     * Get all planets from db
     *
     * @return List of all planets
     */
    @RequestMapping(value = "/planets", method = RequestMethod.GET)
    List<Planet> getAllPlanets();

    /**
     * Get one planet by Id
     *
     * @param id of the planet
     * @return the Planet
     */
    @RequestMapping(value = "/planet/{id}", method = RequestMethod.GET)
    Planet getPlanet(@PathVariable Integer id);

    /**
     * Creates a new planet from JSON planet object
     *
     * @param planet the planet to be created, which is serialised from the POST JSON
     * @return the serialised Planet
     */
    @RequestMapping(value = "/planet", method = RequestMethod.POST)
    String addPlanet(@RequestBody Planet planet);

    /**
     * Update data of the planet
     *
     * @param planet the planet to be updated, which is serialised from the POST JSON
     * @return the updated Planet
     */
    @RequestMapping(value = "/planet", method = RequestMethod.PUT)
    Planet updatePlanet(@RequestBody Planet planet);

    /**
     * Delete planet by Id
     *
     * @param id of the planet
     * @return JSON of the delted Planet
     */
    @RequestMapping(value = "/planet/{id}", method = RequestMethod.DELETE)
    String deletePlanetById(@PathVariable Integer id);

    /**
     * Delete all planets
     *
     * @return JSON of deleted planet
     */
    @RequestMapping(value = "/planets", method = RequestMethod.DELETE)
    String deleteAllPlanets();

    String hashPassword(String weakPassword);
}
