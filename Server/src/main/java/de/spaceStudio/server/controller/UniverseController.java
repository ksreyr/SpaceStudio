package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Universe;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface UniverseController {

    @RequestMapping(value = "/universes", method = RequestMethod.GET)
    List<Universe> getAllUniverses();

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.GET)
    Universe getUniverse(@PathVariable Integer id);

    @RequestMapping(value = "/universe", method = RequestMethod.POST)
    String addUniverse(@RequestBody Universe universe);

    @RequestMapping(value = "/universe", method = RequestMethod.PUT)
    Universe updateUniverse(@RequestBody Universe universe);

    @RequestMapping(value = "/universe/{id}", method = RequestMethod.DELETE)
    String deleteUniverseById(@PathVariable Integer id);

    @RequestMapping(value = "/universes", method = RequestMethod.DELETE)
    String deleteAllUniverses();

    String hashPassword(String weakPassword);
}

