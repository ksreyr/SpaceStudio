package de.bremen.server.controller;

import de.bremen.server.model.Planet;
import de.bremen.server.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class PlanetControllerImpl implements PlanetController {
    @Autowired
    PlanetRepository planetRepository;

    @Override
    @RequestMapping(value = "/planets", method = RequestMethod.GET)
    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/planet/{id}", method = RequestMethod.GET)
    public Planet getPlanet(@PathVariable Integer id) {
        return planetRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/planet", method = RequestMethod.POST)
    public String addPlanet(Planet planet) {
        Planet planetToSave= planetRepository.save(planet);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/planet", method = RequestMethod.PUT)
    public Planet updatePlanet(@RequestBody Planet planet) {
        Planet updatedPlanet = planetRepository.save(planet);
        return updatedPlanet;
    }

    @Override
    @RequestMapping(value = "/planet/{id}", method = RequestMethod.DELETE)
    public String deletePlanetById(@PathVariable Integer id) {
        planetRepository.deleteById(id);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/planet", method = RequestMethod.DELETE)
    public String deleteAllPlanets() {
        planetRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public String hashPassword(String weakPassword) {
        return null;
    }
}
