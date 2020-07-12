package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.SectionRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
import de.spaceStudio.server.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WeaponControllerImpl implements WeaponController {
    @Autowired
    WeaponRepository weaponRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    StopAbstractRepository stopAbstractRepository;


    @Override
    public Ship calculateDammage(Weapon w, Section sec, Ship s) {
        return null;
    }

    @Override
    @RequestMapping(value = "/weapons", method = RequestMethod.GET)
    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    @Override
    @RequestMapping(value = "/weapon/{id}", method = RequestMethod.GET)
    public Weapon getWeapon(@PathVariable Integer id) {
        return weaponRepository.getOne(id);
    }

    @Override
    @RequestMapping(value = "/weapon", method = RequestMethod.POST)
    public String addWeapon(@RequestBody Weapon weapon) {
        Ship ship = weapon.getSection().getShip();
        ship = shipRepository.findShipByName(ship.getName()).get();
        List<Section> sections = sectionRepository.findAllByShip(ship).get();
        for (Section s : sections) {
            if (s.getSectionTyp().equals(SectionTyp.WEAPONS)) {
                weapon.setSection(s);
                break;
            }
        }
        weaponRepository.save(weapon);
        return HttpStatus.CREATED.toString();
    }

    @RequestMapping(value = "/listweapons", method = RequestMethod.POST)
    public String addWeapons(@RequestBody List<Weapon> weapons) {
        List<Weapon> weaponList= new ArrayList<Weapon>();
        for (Weapon w :
                weapons) {
            weaponRepository.save(w);
            weaponList.add(w);
        }
        Gson gson= new Gson();
        return gson.toJson(weaponList);
    }

    @Override
    @RequestMapping(value = "/weapon", method = RequestMethod.PUT)

    public Weapon updateWeapon(@RequestBody Weapon weapon) {
        Weapon weapon1 = weaponRepository.save(weapon);
        return weapon1;
    }

    @Override
    @RequestMapping(value = "/weapon/{id}", method = RequestMethod.DELETE)

    public String deleteWeaponById(@PathVariable Integer id) {
        weaponRepository.deleteById(id);
        return HttpStatus.ACCEPTED.toString();
    }

    @Override
    @RequestMapping(value = "/weapons", method = RequestMethod.DELETE)

    public String deleteAllWeapons() {
        weaponRepository.deleteAll();
        return HttpStatus.OK.toString();
    }

    @Override
    public String fire(@RequestBody  Weapon weapon) {
        Ship ship=weapon.getSection().getShip();
        ship=shipRepository.findShipByName(ship.getName()).get();
        Ship gegnerShip=new Ship();
        StopAbstract planet= stopAbstractRepository.findByShips(ship).get();
        List<Ship> shipList= planet.getShips();

        for (Ship s :
                shipList) {
            if(!s.equals(ship)){
                gegnerShip=s;
            }
        }
        List<Section> sectionList=sectionRepository.findAllByShip(gegnerShip).get();
        for (Section s :
                sectionList) {
            if (weapon.getObjectiv().getSectionTyp().equals(s.getSectionTyp())) {
                s.setUsable(false);
                sectionRepository.save(s);
            }
        }
        gegnerShip.setHp(gegnerShip.getHp() - weapon.getDamage());

        shipRepository.save(gegnerShip);
        return HttpStatus.OK.toString();
    }

    @Override
    public String shotValidation(List<Weapon> weapons) {
        return null;
    }
}
