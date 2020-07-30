package de.spaceStudio.server.controller;

import com.google.gson.Gson;
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
import java.util.stream.Collectors;

@RestController
public class SectionControllerImpl implements SectionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCore.class);
    @Autowired
    WeaponRepository weaponRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    GameController gameController;
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

    /**
     * Get all sections from db
     *
     * @return List of all sections
     */
    @Override
    public List<Section> getAllSections() {
        return repository.findAll();
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

        if (playerRepository.findByName(section.getShip().getOwner().getName()).isPresent()) {
            playerToUpdate = playerRepository.findByName(section.getShip().getOwner().getName()).get();
            Ship ship = shipRepository.findShipByNameAndOwner(section.getShip().getName(), playerToUpdate).get();
            ship.setOwner(playerToUpdate);
            section.setShip(ship);
        } else {
            ai = aiRepository.findByName(section.getShip().getOwner().getName()).get();
            Ship ship = shipRepository.findShipByNameAndOwner(section.getShip().getName(), ai).get();
            ship.setOwner(ai);
            section.setShip(ship);
        }

        repository.save(section);
        return section.toString();
    }

    @RequestMapping(value = "/sectiontoadd", method = RequestMethod.POST)
    public String addSections(@RequestBody ArrayList<Section> sections) {

        List<Section> sectionsadded = new ArrayList<>();
        for (Section s :
                sections) {
            Section section = repository.save(s);
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

        return repository.findAllByShip(ship.get()).get();
    }

    @Override
    public List<Section> updateEnergy(List<Section> sectionsToUpdate) {
        if (sectionsToUpdate.size() > 0) {

            boolean allEqual = true;
            for (Section s : sectionsToUpdate) {
                if (!s.getShip().getId().equals(sectionsToUpdate.get(0).getShip().getId())) {
                    allEqual = false;
                    System.out.println("Hier wurde break ausgeführt! Zeile 153");
                    break;
                }
            }
            int sumCurrent = sectionsToUpdate.stream().mapToInt(Section::getPowerCurrent).sum();
            int sumRequired = sectionsToUpdate.stream().mapToInt(Section::getPowerRequired).sum();

            if (sumRequired == gameController.sumRequiredPower(sectionsToUpdate.get(0).getShip())
                    && sumCurrent <= sectionsToUpdate.get(0).getShip().getPower() && allEqual) {
                sectionRepository.saveAll(sectionsToUpdate);
                LOGGER.info("Updating Energy of Sections");
            }
        }
        return sectionRepository.findAllById(sectionsToUpdate.stream().map(Section::getId).collect(Collectors.toList()));
    }

    @Override
    public List<Section> makeChanges(List<Section> sections) {

        for (Section s :
                sections) {
            Optional<CrewMember> crew = crewMemberRepository.findByCurrentSection(s);
            if (crew.isPresent() && crew.get().getRole().equals(s.getRole())) {
                crew.get().setSkillCounter(crew.get().getSkillCounter() + 1);
                if (crew.get().getRoundsToDestination() <= 0) { // is not Traveling
                    switch (crew.get().getRole()) {
                        case FIGHTER:
                            Optional<List<Weapon>> weapons = weaponRepository.findBySection(s);
                            if (weapons.isPresent()) { // More Power is Crew is in Sections
                                int powerBuffFighter = s.getPowerCurrent() + 1;
                                s.setPowerCurrent(powerBuffFighter);
                                for (Weapon w :
                                        weapons.get()) {
                                    int damage = w.getDamage() + (10 + crew.get().getSkillCounter());
                                    w.setDamage(damage);
                                }
                            }
                        case TECHNICIAN:
                            int powerBuffTechnician = s.getPowerCurrent() + (10 + crew.get().getSkillCounter());
                            s.setPowerCurrent(powerBuffTechnician);
                    }
                } else {
                    crew.get().setRoundsToDestination(crew.get().getRoundsToDestination() - 1);
                }
                crewMemberRepository.save(crew.get());

            }
            if (crew.isPresent()) {
                s.setUsable(true); // Reapir if Broken
            }
            if (crew.isPresent() && s.getOxygen() < 30) {
                crewMemberRepository.delete(crew.get()); // Die if to little Oxygen
            }
            sectionRepository.save(s);
        }

        return sections;
    }
}
