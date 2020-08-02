package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StationControllerImpl implements StationController {
    @Autowired
    StationRepository stationRepository;

    @Autowired
    UniverseRepository universeRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    AIRepository aiRepository;

    @Override
    @RequestMapping(value = "/stations", method = RequestMethod.GET)
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
        //stationRepository.save(station);
        Universe universe = universeRepository.findByName(station.getUniverse().getName()).get();
        Player p1 = new Player();
        AI ai = new AI();
        Ship shipReal = new Ship();
        try {
            ArrayList<Ship> shipList = (ArrayList<Ship>) station.getShips();
            for (Ship ship :
                    shipList) {
                if (ship.getOwner() != null) {
                    if (playerRepository.findByName(ship.getOwner().getName()).isPresent()) {
                        p1 = playerRepository.findByName(ship.getOwner().getName()).get();
                        shipReal = ship;
                        shipReal = shipRepository.
                                findShipByNameAndOwner(shipReal.getName(), p1).get();
                    } else {
                        ai = aiRepository.findByName(ship.getOwner().getName()).get();
                        shipReal = ship;
                        shipReal = shipRepository.
                                findShipByNameAndOwner(shipReal.getName(), ai).get();
                    }
                    shipList.add(shipReal);
                    shipList.remove(ship);
                }
                station.setShips(shipList);
                station.setUniverse(universe);
                stationRepository.save(station);
                return HttpStatus.OK.toString();
            }
        } catch (Exception e) {
            station.setShips(null);
            station.setUniverse(universe);
            stationRepository.save(station);
            return HttpStatus.OK.toString();
        }
        return HttpStatus.OK.toString();
    }

    @RequestMapping(value = "/liststation", method = RequestMethod.POST)
    public String addStation(@RequestBody List<Station> stations) {
        //stationRepository.save(station);
        /*Universe universe= universeRepository.findByName(station.getUniverse().getName()).get();
        Player p1 = new Player();
        AI ai = new AI();
        Ship shipReal = new Ship();
        try{
            ArrayList<Ship> shipList= (ArrayList<Ship>) station.getShips();
            for (Ship ship :
                    shipList) {
                if(ship.getOwner()!=null){
                    if (playerRepository.findByName(ship.getOwner().getName()).isPresent()) {
                        p1 = playerRepository.findByName(ship.getOwner().getName()).get();
                        shipReal = ship;
                        shipReal = shipRepository.
                                findShipByNameAndAndOwner(shipReal.getName(), p1).get();
                    }else{
                        ai = aiRepository.findByName(ship.getOwner().getName()).get();
                        shipReal = ship;
                        shipReal = shipRepository.
                                findShipByNameAndAndOwner(shipReal.getName(), ai).get();
                    }
                    shipList.add(shipReal);
                    shipList.remove(ship);
                }
                station.setShips(shipList);
                station.setUniverse(universe);
                stationRepository.save(station);
                return HttpStatus.OK.toString();
            }}catch (Exception e){
            station.setShips(null);
            station.setUniverse(universe);
            stationRepository.save(station);
            return HttpStatus.OK.toString();
        }*/
        List<Station> stationList = new ArrayList<>();
        for (Station s :
                stations) {
            Station station = stationRepository.save(s);
            stationList.add(s);
        }
        Gson gson = new Gson();

        return gson.toJson(stationList);
    }

    @Override
    @RequestMapping(value = "/station", method = RequestMethod.PUT)
    public Station updateStation(@RequestBody Station station) {
        Station stationToUpdate = stationRepository
                .findById(station.getId()).get();
        stationToUpdate = station;
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
    @RequestMapping(value = "/stations", method = RequestMethod.DELETE)
    public String deleteAllStations() {
        stationRepository.deleteAll();
        return HttpStatus.OK.toString();
    }


}
