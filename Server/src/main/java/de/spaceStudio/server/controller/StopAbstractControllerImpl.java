package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StopAbstractControllerImpl implements StopAbstractController {
    @Autowired
    StopAbstractRepository stopAbstractRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;

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
    public String makeJump(@RequestBody List<StopAbstract> stops) {

        StopAbstract stopStart = stops.get(0);
        stops.remove(stopStart);
        StopAbstract stopEnd = stops.get(0);
        Player player=new Player();
        Ship ship= new Ship();
        ArrayList<Ship> ships= (ArrayList<Ship>) stopStart.getShips();
        for (Ship s :
                ships) {
            if(s.getOwner()!=null){
                player= playerRepository.findByName(s.getOwner().getName()).get();
                ship=s;
            }
        }
        
        ship= shipRepository.findShipByNameAndAndOwner(ship.getName(),player).get();
        stopStart= stopAbstractRepository.findByShips(ship).get();
        List<StopAbstract> stopAbstracts = new ArrayList<>();
        stopAbstracts= stopAbstractRepository.findByUniverse(stopStart.getUniverse()).get();
        for (StopAbstract s:
                stopAbstracts) {
            if(s.getName().equals(stopEnd.getName())){
                stopEnd=s;
            }

        }
        //Delete proces
        for (Ship s :
                ships) {
            if(ship.getName().equals(s.getName())){
                ships.remove(s);
                break;
            }
        }
        stopStart.setShips(ships);
        stopAbstractRepository.save(stopStart);
        List<Ship> shipJump= new ArrayList<>();
        shipJump= stopEnd.getShips();
        shipJump.add(ship);
        stopEnd.setShips(shipJump);
        stopAbstractRepository.save(stopEnd);

        return HttpStatus.OK.toString();
    }
}
