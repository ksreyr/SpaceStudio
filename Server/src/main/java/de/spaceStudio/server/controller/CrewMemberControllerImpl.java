package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CrewMemberControllerImpl implements CrewMemberController {
    private static final Logger logger = LoggerFactory.getLogger(CrewMemberControllerImpl.class);
    private static final int ROUNDS_TO_TRAVEL = 1;
    @Autowired
    CrewMemberRepository crewMemberRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    ShipRessourceRepository shipRessourceRepository;

    private int crewMemberCost = 50;

    /**
     * Get all crewmembers from db
     *
     * @return List of all crewmembers
     */
    @Override
    public List<CrewMember> getAllCrewMembers() {
        return crewMemberRepository.findAll();
    }

    /**
     * Get one crewmember by Id
     *
     * @param id of the crewmember
     * @return the Crewmember
     */
    @Override
    public CrewMember getCrewMember(Integer id) {
        return crewMemberRepository.findById(id).get();
    }

    /**
     * Creates a new crewmember from JSON crewmember object
     *
     * @param crewMember the crewmember to be created, which is serialised from the POST JSON
     * @return the serialised Crewmember
     */
    @Override
    public String addCrewMember(CrewMember crewMember) {
        String s1 = crewMember.getCurrentSection().getShip().getName();
        Player p1 = playerRepository.findByName(crewMember.
                getCurrentSection().getShip().getOwner().getName()).get();
        Ship ship = shipRepository.findShipByNameAndOwner(s1, p1).get();
        List<Section> sections = sectionRepository.findAllByShip(ship).get();
        for (Section s :
                sections) {
            if (s.getImg().equals(crewMember.getCurrentSection().getImg())) {
                crewMember.setCurrentSection(s);
                break;
            }
        }
        crewMemberRepository.save(crewMember);
        return HttpStatus.ACCEPTED.toString();
    }

    /**
     * Creates a new crewmember from JSON crewmember object
     *
     * @param crewMembers the crewmember to be created, which is serialised from the POST JSON
     * @return the serialised Crewmember
     */
    @RequestMapping(value = "/crewmemberstoadd", method = RequestMethod.POST)
    public String addCrewMembers(@RequestBody List<CrewMember> crewMembers) {

        List<Section> sections = new ArrayList<>();
        for (CrewMember c :
                crewMembers) {
            crewMemberRepository.save(c);
            sections.add(c.getCurrentSection());
        }
        List<CrewMember> crewMemberListID = new ArrayList<>();
        for (Section s :
                sections) {
            crewMemberListID.add(crewMemberRepository.findByCurrentSection(s).get());
        }
        Gson gson = new Gson();
        gson.toJson(crewMemberListID);
        return gson.toJson(crewMemberListID);
    }

    @RequestMapping(value = "/buycrewmember")
    public String buyCrewMembers(@RequestBody List<CrewMember> crewMembers) {
        Gson gson = new Gson();
        Section section = new Section();
        for (CrewMember crewMember :
                crewMembers) {
            section = sectionRepository.findById(crewMember.getCurrentSection().getId()).get();
            List<ShipRessource> shipRessources = shipRessourceRepository.findByShip(section.getShip()).get();
            for (ShipRessource s :
                    shipRessources) {
                if (s.getName().equals(RessourceName.GOLD)) {
                    if (s.getAmount() - crewMemberCost >= 0) {
                        s.setAmount(s.getAmount() - crewMemberCost);
                        shipRessourceRepository.save(s);
                    } else {
                        return gson.toJson(shipRessources);
                    }

                }
            }

            if (crewMemberRepository.findAllByCurrentSection(section).isPresent()) {
                List<Section> sectionList = sectionRepository.findAllByShip(section.getShip()).get();
                for (Section s :
                        sectionList) {
                    if (crewMemberRepository.findAllByCurrentSection(section).isPresent()) {

                    } else {
                        crewMember.setCurrentSection(section);
                        break;
                    }
                }
            } else {
                crewMember.setCurrentSection(section);
            }

            crewMemberRepository.save(crewMember);
        }
        List<ShipRessource> shipRessources = shipRessourceRepository.findByShip(section.getShip()).get();

        return gson.toJson(shipRessources);
    }

    /**
     * Update data of the crewmember
     *
     * @param crewMember the crewmember to be updated, which is serialised from the POST JSON
     * @return the updated Crewmember
     */
    @Override
    public CrewMember updateCrewMember(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }

    /**
     * Delete crewmember by Id
     *
     * @param id of the crewmember
     * @return JSON of the delted Crewmember
     */
    @Override
    public String deleteCrewMemberById(Integer id) {
        crewMemberRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    /**
     * Delete all crewmembers
     *
     * @return JSON of deleted crewmember
     */
    @Override
    public String deleteAllCrewMembers() {
        crewMemberRepository.deleteAll();
        return HttpStatus.ACCEPTED.toString();
    }


    /**
     * Reassign Crew in the Ship
     *
     * @param crewMember Crew Member to be moved
     * @return the Ship with updated Crew Postions if validated
     */
    @Override
    public CrewMember updatePostion(CrewMember crewMember) {
        Section sectionNew = crewMember.getCurrentSection();

        Optional<CrewMember> crewMemberOld = crewMemberRepository.findById(crewMember.getId());

        if (!crewMemberOld.isPresent()) {
            throw new IllegalArgumentException("Crew Member was not in a Section");
        }


        Optional<Object> sectionOld = Optional.empty();
        if (crewMemberOld.isPresent()) {
            sectionOld = Optional.ofNullable(crewMemberOld.get().getCurrentSection());

            if (!sectionOld.get().equals(sectionNew)) {
                if (crewMemberRepository.findByCurrentSection(sectionNew).isEmpty() && !(sectionNew.equals(sectionOld.get()))) {
                    crewMember.setRoundsToDestination(ROUNDS_TO_TRAVEL);
                    crewMemberRepository.save(crewMember);
                    return crewMember;
                }
            }
        }
        return crewMemberOld.get();
    }

    @Override
    public List<CrewMember> getMembers(@PathVariable Integer id) {
        Optional<Ship> ship = shipRepository.findById(id);
        if (ship.isEmpty()) {
            return null;
        }
        List<Section> sections = sectionRepository.findAllByShip(ship.get()).get();
        List<CrewMember> crewMembers = new ArrayList<>();
        for (Section s : sections) {
            Optional<ArrayList<CrewMember>> crews = crewMemberRepository.findAllByCurrentSection(s);
            if (crews.isPresent()) {
                crewMembers.addAll(crews.get());
            }
        }
        return crewMembers;
    }
}
