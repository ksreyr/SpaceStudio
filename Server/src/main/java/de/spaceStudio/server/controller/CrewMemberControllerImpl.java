package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.repository.CrewMemberRepository;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.SectionRepository;
import de.spaceStudio.server.repository.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CrewMemberControllerImpl implements CrewMemberController {
    private static final Logger logger = LoggerFactory.getLogger(CrewMemberControllerImpl.class);
    @Autowired
    CrewMemberRepository crewMemberRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;

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
        Ship ship = shipRepository.findShipByNameAndAndOwner(s1, p1).get();
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
     * @param shipID
     * @param crewMember which has been moved
     * @param sectionNew to where he will be moved
     * @param sectionOld from where he will be moved
     * @return the Ship with updated Crew Postions if validated
     */
    @Override
    public CrewMember updatePostion(int shipID, CrewMember crewMember, Section sectionNew, Section sectionOld) {
        Optional<Ship> oldShip = shipRepository.findById(shipID);

        if (crewMemberRepository.findById(shipID).get().getCurrentSection() != sectionOld) {
            logger.error("Illegal Request made from Client updatePostion(" + crewMember.getId() + "to " + sectionNew + ")");
            return crewMember;
        } else {
            CrewMember c = crewMemberRepository.findById(crewMember.getId()).get();
            c.setCurrentSection(sectionNew);
            crewMemberRepository.save(c);
            return c;
        }
    }
}
