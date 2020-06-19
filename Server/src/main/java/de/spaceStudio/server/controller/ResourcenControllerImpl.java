package de.bremen.server.controller;

import de.bremen.server.model.Ressource;
import de.bremen.server.repository.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class ResourcenControllerImpl implements ResourcenController {
    @Autowired
    private RessourceRepository ressourceRepository;

    @Override
    @RequestMapping(value = "/ressources", method = RequestMethod.GET)
    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/ressources/{id}", method = RequestMethod.GET)
    public Ressource getRessource(@PathVariable Integer id) {
        return ressourceRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/ressources", method = RequestMethod.POST)
    public String addRessource(@RequestBody Ressource ressource) {
        ressourceRepository.save(ressource);
        return ressource.toString();
    }

    @Override
    @RequestMapping(value = "/ressources", method = RequestMethod.PUT)
    public Ressource updateRessource(@RequestBody Ressource ressource) {
        Ressource ressourceToUpdate = ressourceRepository.getOne(ressource.getId());
        ressourceToUpdate = ressource;
        ressourceRepository.save(ressourceToUpdate);
        return ressourceToUpdate;
    }

    @Override
    @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
    public String deleteRessourceById(@PathVariable Integer id) {
        ressourceRepository.deleteById(id);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/section", method = RequestMethod.DELETE)
    public String deleteAllRessources() {
        ressourceRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public String hashPassword(String weakPassword) {
        return null;
    }
}
