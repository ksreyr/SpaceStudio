package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Station;
import de.spaceStudio.server.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class StationControllerImpl implements StationController{
    @Autowired
    StationRepository stationRepository;

    @Override
    @RequestMapping(value = "/station", method = RequestMethod.GET)
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/station/{id}", method = RequestMethod.GET)
    public Station getStation(@PathVariable Integer id) {
        return stationRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/station", method = RequestMethod.POST)
    public String addStation(@RequestBody Station station) {
        stationRepository.save(station);
        return HttpStatus.OK.toString();
    }

    @Override
    @RequestMapping(value = "/station", method = RequestMethod.PUT)
    public Station updateStation(@RequestBody Station station) {
        Station stationToUpdate= stationRepository
                .findById(station.getId()).get();
        stationToUpdate=station;
        stationRepository.save(stationToUpdate);
        return stationToUpdate;
    }

    @Override
    @RequestMapping(value = "/station/{id}", method = RequestMethod.DELETE)
    public String deleteStationById(@PathVariable Integer id) {
        stationRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    @RequestMapping(value = "/section", method = RequestMethod.DELETE)
    public String deleteAllStations() {
        stationRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public String hashPassword(String weakPassword) {
        return null;
    }
}
