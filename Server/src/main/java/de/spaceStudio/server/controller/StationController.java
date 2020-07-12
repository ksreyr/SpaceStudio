package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Station;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface StationController {

    /**
     * Get all stations from db
     *
     * @return List of all stations
     */
    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    List<Station> getAllStations();

    /**
     * Get one station by Id
     *
     * @param id of the station
     * @return the Station
     */
    @RequestMapping(value = "/station/{id}", method = RequestMethod.GET)
    Station getStation(@PathVariable Integer id);

    /**
     * Creates a new station from JSON station object
     *
     * @param station the station to be created, which is serialised from the POST JSON
     * @return the serialised Station
     */
    @RequestMapping(value = "/station", method = RequestMethod.POST)
    String addStation(@RequestBody Station station);

    /**
     * Update data of the station
     *
     * @param station the station to be updated, which is serialised from the POST JSON
     * @return the updated Station
     */
    @RequestMapping(value = "/station", method = RequestMethod.PUT)
    Station updateStation(@RequestBody Station station);

    /**
     * Delete station by Id
     *
     * @param id of the station
     * @return JSON of the delted Station
     */
    @RequestMapping(value = "/station/{id}", method = RequestMethod.DELETE)
    String deleteStationById(@PathVariable Integer id);

    /**
     * Delete all stations
     *
     * @return JSON of deleted station
     */
    @RequestMapping(value = "/stations", method = RequestMethod.DELETE)
    String deleteAllStations();


}
