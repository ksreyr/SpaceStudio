package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import com.google.gson.internal.GsonBuildConfig;
import de.spaceStudio.server.ServerCore;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SectionControllerImpl implements SectionController {
    @Autowired
    private SectionRepository repository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private AIRepository aiRepository;

    @Autowired
    private CrewMemberRepository crewMemberRepository;

    @Autowired WeaponRepository weaponRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCore.class);


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
        Player playerToUpdate;
        AI ai;

        if(playerRepository.findByName(section.getShip().getOwner().getName()).isPresent()){
            playerToUpdate=playerRepository.findByName(section.getShip().getOwner().getName()).get();
            Ship ship = shipRepository.findShipByNameAndAndOwner(section.getShip().getName(),playerToUpdate).get();
            ship.setOwner(playerToUpdate);
            section.setShip(ship);
        }else{
            ai=aiRepository.findByName(section.getShip().getOwner().getName()).get();
            Ship ship = shipRepository.findShipByNameAndAndOwner(section.getShip().getName(),ai).get();
            ship.setOwner(ai);
            section.setShip(ship);
        }

        repository.save(section);
        return section.toString();
    }

    @RequestMapping(value = "/sectiontoadd", method = RequestMethod.POST)
    public String addSections(@RequestBody ArrayList<Section> sections) {
        Player playerToUpdate;
        AI ai;
        Ship ship=sections.get(0).getShip();
        /*if(playerRepository.findByName(section.getShip().getOwner().getName()).isPresent()){
            playerToUpdate=playerRepository.findByName(section.getShip().getOwner().getName()).get();
            Ship ship = shipRepository.findShipByNameAndAndOwner(section.getShip().getName(),playerToUpdate).get();
            ship.setOwner(playerToUpdate);
            section.setShip(ship);
        }else{
            ai=aiRepository.findByName(section.getShip().getOwner().getName()).get();
            Ship ship = shipRepository.findShipByNameAndAndOwner(section.getShip().getName(),ai).get();
            ship.setOwner(ai);
            section.setShip(ship);
        }*/
        List<Section> sectionsadded= new ArrayList<>();
        for (Section s :
                sections) {
            Section section= repository.save(s);
            sectionsadded.add(section);
        }
        Gson gson = new Gson();
        gson.toJson(sectionsadded);
        return gson.toJson(sectionsadded);
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

    @Override
    public List<Section> sectionsByShip(Integer id) {
        Optional<Ship> ship = shipRepository.findById(id);
        if (ship.isEmpty()) {
            return null;
        }
        List<Section> secs = repository.findAllByShip(ship.get()).get();

        secs = makeChanges(secs);

        return secs;
    }

    private List<Section> makeChanges(List<Section> secs) {

        for (Section s :
                secs) {

            Optional<CrewMember> crew = crewMemberRepository.findByCurrentSection(s);

            if (crew.isPresent() && crew.get().getRole().equals(s.getRole())) {
                switch (crew.get().getRole()) {
                    case FIGHTER:
                        Optional<List<Weapon>> weapons = weaponRepository.findBySection(s);
                        if (weapons.isPresent()) { // More Power is Crew is in Sections
                            s.setPowerCurrent(s.getPowerCurrent() + 1);
                        }

                }
            }
        }
        return secs;
    }
}
