package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.repository.CrewMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class CrewMemberControllerImpl implements CrewMemberController {
    @Autowired
    CrewMemberRepository crewMemberRepository;

    @Override
    @RequestMapping(value = "/crewmembers", method = RequestMethod.GET)
    public List<CrewMember> getAllCrewMembers() {
        return crewMemberRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/crewmember/{id}", method = RequestMethod.GET)
    public CrewMember getCrewMember(@PathVariable Integer id) {
        return crewMemberRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/crewmember", method = RequestMethod.POST)
    public String addCrewMember(@RequestBody CrewMember crewMember) {
        crewMemberRepository.save(crewMember);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/crewmember", method = RequestMethod.PUT)
    public CrewMember updateCrewMember(@RequestBody CrewMember crewMember) {
        CrewMember crewMemberToUpdate= crewMemberRepository
                .getOne(crewMember.getId());
        crewMemberToUpdate = crewMember;
        crewMemberRepository.save(crewMemberToUpdate);
        return crewMemberToUpdate;
    }

    @Override
    @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
    public String deleteCrewMemberById(@PathVariable Integer id) {
        crewMemberRepository.deleteById(id);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/section", method = RequestMethod.DELETE)
    public String deleteAllCrewMembers() {
        crewMemberRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public CrewMember updatePostion(int shipID, CrewMember crewMember, Section sectionNew, Section sectionOld) {
        return null;
    }
}
