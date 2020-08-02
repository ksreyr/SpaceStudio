package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PlanetControllerImpl implements PlanetController {
    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    UniverseRepository universeRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    AIRepository aiRepository;

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
    public String addPlanet(@RequestBody Planet planet) {
        Universe universe = universeRepository.findByName(planet.getUniverse().getName()).get();
        Player p1 = new Player();
        AI ai = new AI();
        Ship shipReal = new Ship();
        try {
            ArrayList<Ship> shipList = (ArrayList<Ship>) planet.getShips();
            for (Ship ship :
                    shipList) {
                if (ship.getOwner() != null) {
                    if (playerRepository.findByName(ship.getOwner().getName()).isPresent()) {
                        p1 = playerRepository.findByName(ship.getOwner().getName()).get();
                        shipReal = ship;
                        shipReal = shipRepository.
                                findShipByNameAndOwner(shipReal.getName(), p1).get();
                    } else {
                        ai = aiRepository.findByName(ship.getOwner().getName()).get();
                        shipReal = ship;
                        shipReal = shipRepository.
                                findShipByNameAndOwner(shipReal.getName(), ai).get();
                    }
                    shipList.add(shipReal);
                    shipList.remove(ship);
                }
                planet.setShips(shipList);
                planet.setUniverse(universe);
                planetRepository.save(planet);
                return HttpStatus.OK.toString();
            }
        } catch (Exception e) {
            planet.setShips(null);
            planet.setUniverse(universe);
            planetRepository.save(planet);
            return HttpStatus.OK.toString();
        }
        return HttpStatus.OK.toString();

    }

    @RequestMapping(value = "/listplanet", method = RequestMethod.POST)
    public String addPlanets(@RequestBody List<Planet> planets) {
        List<Planet> planetadded = new ArrayList<>();
        for (Planet p :
                planets) {
            Planet planet = planetRepository.save(p);
            planetadded.add(p);
        }
        Gson gson = new Gson();
        return gson.toJson(planetadded);
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

}
