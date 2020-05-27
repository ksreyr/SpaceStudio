package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.StopAbstract;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface StopAbstractController {

    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.GET)
    List<StopAbstract> getAllStopAbstracts();

    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.GET)
    StopAbstract getStopAbstract(@PathVariable Integer id);

    @RequestMapping(value = "/stopAbstract", method = RequestMethod.POST)
    String addStopAbstract(@RequestBody StopAbstract stopAbstract);

    @RequestMapping(value = "/stopAbstract", method = RequestMethod.PUT)
    StopAbstract updateStopAbstract(@RequestBody StopAbstract stopAbstract);

    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.DELETE)
    String deleteStopAbstractById(@PathVariable Integer id);

    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.DELETE)
    String deleteAllStopAbstracts();

    String hashPassword(String weakPassword);
}
