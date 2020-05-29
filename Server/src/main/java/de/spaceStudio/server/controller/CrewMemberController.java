package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.CrewMember;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface CrewMemberController {
    /**
     * Get all crewmembers from db
     * @return List of all crewmembers
     */
    @RequestMapping(value = "/crewMembers", method = RequestMethod.GET)
    List<CrewMember> getAllCrewMembers();
    /**
     * Get one crewmember by Id
     *
     * @param id of the crewmember
     * @return the Crewmember
     */
    @RequestMapping(value = "/crewMember/{id}", method = RequestMethod.GET)
    CrewMember getCrewMember(@PathVariable Integer id);
    /**
     * Creates a new crewmember from JSON crewmember object
     * @param crewMember the crewmember to be created, which is serialised from the POST JSON
     * @return the serialised Crewmember
     */
    @RequestMapping(value = "/crewMember", method = RequestMethod.POST)
    String addCrewMember(@RequestBody CrewMember crewMember);
    /**
     * Update data of the crewmember
     * @param crewMember the crewmember to be updated, which is serialised from the POST JSON
     * @return the updated Crewmember
     */
    @RequestMapping(value = "/crewMember", method = RequestMethod.PUT)
    CrewMember updateCrewMember(@RequestBody CrewMember crewMember);
    /**
     * Delete crewmember by Id
     * @param id of the crewmember
     * @return JSON of the delted Crewmember
     */
    @RequestMapping(value = "/crewMember/{id}", method = RequestMethod.DELETE)
    String deleteCrewMemberById(@PathVariable Integer id);
    /**
     * Delete all crewmembers
     * @return JSON of deleted crewmember
     */
    @RequestMapping(value = "/crewMembers", method = RequestMethod.DELETE)
    String deleteAllCrewMembers();

    String hashPassword(String weakPassword);
}

