package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.SectionRepository;
import de.spaceStudio.server.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SectionControllerImpl implements SectionController {
    @Autowired
    private SectionRepository repository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ShipRepository shipRepository;
    /**
     * Get all sections from db
     *
     * @return List of all sections
     */
    @Override
    public List<Section> getAllSections() {
        return (List<Section>) repository.findAll();
    }

    /**
     * Get one section by Id
     *
     * @param id of the section
     * @return the Section
     */
    @Override
    public Section getSection(Integer id) {
        return null;
    }

    /**
     * Creates a new section from JSON section object
     *
     * @param section the section to be created, which is serialised from the POST JSON
     * @return the serialised Section
     */
    @Override
    @RequestMapping(value = "/section", method = RequestMethod.POST)
    public String addSection(@RequestBody Section section) {
        Player playerToUpdate=playerRepository.findByName(section.getShip().getOwner().getName()).get();
        Ship ship = shipRepository.findShipByNameAndAndOwner(section.getShip().getName(),playerToUpdate).get();
        ship.setOwner(playerToUpdate);
        section.setShip(ship);
        repository.save(section);
        return section.toString();
    }

    /**
     * Update data of the section
     *
     * @param section the section to be updated, which is serialised from the POST JSON
     * @return the updated Section
     */
    @Override
    public Section updateSection(Section section) {
        return null;
    }

    /**
     * Delete section by Id
     *
     * @param id of the section
     * @return JSON of the delted Section
     */
    @Override
    public String deleteSectionById(Integer id) {
        return null;
    }

    /**
     * Delete all sections
     *
     * @return JSON of deleted section
     */
    @Override
    public String deleteAllSections() {
        return null;
    }
}
