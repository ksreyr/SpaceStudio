package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.repository.StopAbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class StopAbstractControllerImpl implements StopAbstractController {
    @Autowired
    StopAbstractRepository stopAbstractRepository;

    @Override
    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.GET)
    public List<StopAbstract> getAllStopAbstracts() {
        return stopAbstractRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.GET)
    public StopAbstract getStopAbstract(@PathVariable Integer id) {
        return stopAbstractRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/stopAbstract", method = RequestMethod.POST)
    public String addStopAbstract(@RequestBody StopAbstract stopAbstract) {
        stopAbstractRepository.save(stopAbstract);
        return HttpStatus.CREATED.toString();
    }

    @Override
    @RequestMapping(value = "/stopAbstract", method = RequestMethod.PUT)
    public StopAbstract updateStopAbstract(@RequestBody StopAbstract stopAbstract) {
        StopAbstract stopAbstractToUpdate= stopAbstractRepository.save(stopAbstract);
        return stopAbstractToUpdate;
    }

    @Override
    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.DELETE)
    public String deleteStopAbstractById(@PathVariable Integer id) {
        stopAbstractRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.DELETE)
    public String deleteAllStopAbstracts() {
        stopAbstractRepository.deleteAll();
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    public String hashPassword(String weakPassword) {
        return null;
    }
}
