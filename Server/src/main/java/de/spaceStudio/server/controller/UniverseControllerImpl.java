package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Planet;
import de.spaceStudio.server.model.Universe;
import de.spaceStudio.server.repository.PlanetRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
import de.spaceStudio.server.repository.UniverseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UniverseControllerImpl implements UniverseController {
    @Autowired
    UniverseRepository universeRepository;

    @Autowired
    StopAbstractRepository stopAbstractRepository;

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
    //@RequestMapping(value = "/universe", method = RequestMethod.POST)
    public String addUniverse(@RequestBody Universe universe) {
        Universe updateUniverse = universeRepository.save(universe);

        Optional<Planet> p9 = stopAbstractRepository.findByName("p9");
        if (p9.isPresent()) {
            p9.get().setUniverse(updateUniverse);
            stopAbstractRepository.save(p9.get());
        }
        return updateUniverse.getId().toString();
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

}
