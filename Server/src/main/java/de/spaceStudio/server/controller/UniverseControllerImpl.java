package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Universe;
import de.spaceStudio.server.repository.UniverseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class UniverseControllerImpl implements UniverseController{
    @Autowired
    UniverseRepository universeRepository;

    @Override
    @RequestMapping(value = "/universes", method = RequestMethod.GET)
    public List<Universe> getAllUniverses() {
        return universeRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/universe/{id}", method = RequestMethod.GET)
    public Universe getUniverse(@PathVariable Integer id) {
        return universeRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/universe", method = RequestMethod.POST)
    public String addUniverse(@RequestBody Universe universe) {
        universeRepository.save(universe);
        return HttpStatus.CREATED.toString();
    }

    @Override
    @RequestMapping(value = "/universe", method = RequestMethod.PUT)
    public Universe updateUniverse(@RequestBody Universe universe) {
        Universe updateUniverse = universeRepository.save(universe);
        return updateUniverse;
    }

    @Override
    @RequestMapping(value = "/universe/{id}", method = RequestMethod.DELETE)
    public String deleteUniverseById(@PathVariable Integer id) {
        universeRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    @RequestMapping(value = "/universes", method = RequestMethod.DELETE)
    public String deleteAllUniverses() {
        universeRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public String hashPassword(String weakPassword) {
        return null;
    }
}
