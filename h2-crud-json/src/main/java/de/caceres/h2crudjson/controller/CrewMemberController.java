package de.caceres.h2crudjson.controller;

import de.caceres.h2crudjson.model.CrewMember;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface CrewMemberController {
    @RequestMapping(value = "/crewMember/login", method = RequestMethod.POST)
    String loginUser(@RequestBody CrewMember crewMember);

    @RequestMapping(value = "/crewMembers", method = RequestMethod.GET)
    List<CrewMember> getAllCrewMembers();

    @RequestMapping(value = "/crewMember/{id}", method = RequestMethod.GET)
    CrewMember getCrewMember(@PathVariable Integer id);

    @RequestMapping(value = "/crewMember", method = RequestMethod.POST)
    String addCrewMember(@RequestBody CrewMember crewMember);

    @RequestMapping(value = "/crewMember", method = RequestMethod.PUT)
    CrewMember updateCrewMember(@RequestBody CrewMember crewMember);

    @RequestMapping(value = "/crewMember/{id}", method = RequestMethod.DELETE)
    String deleteCrewMemberById(@PathVariable Integer id);

    @RequestMapping(value = "/crewMembers", method = RequestMethod.DELETE)
    String deleteAllCrewMembers();

    String hashPassword(String weakPassword);
}

