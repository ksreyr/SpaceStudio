package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.SectionTyp;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import de.spaceStudio.server.repository.SectionRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
import de.spaceStudio.server.repository.WeaponRepository;
import org.hibernate.hql.internal.ast.tree.AggregatedSelectExpression;
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
            Weapon weapon =weaponRepository.save(w);
            weaponList.add(weapon);
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
    public String fire(@RequestBody List<Weapon> weapons) {

        Ship ship=new Ship();
        for (Weapon weapon :
                weapons) {
            //Search the objective
            ship = shipRepository.findById(weapon.getObjectiv().getShip().getId()).get();
            if (ship.getShield() > 0) {
                ship.setShield(0);
            }else{
                //Without_Schield
                ship.setHp(ship.getHp()-weapon.getDamage());
                weapon.getObjectiv().setUsable(false);
                sectionRepository.save(weapon.getObjectiv());
            }
            shipRepository.save(ship);
        }
        List<Section> sectionToSend = sectionRepository.findAllByShip(ship).get();
        Gson gson = new Gson();

        return gson.toJson(sectionToSend);
    }

    @Override
    public String shotValidation(List<Weapon> weapons) {
        for (Weapon w :
                weapons) {
            if (w.getObjectiv() != null) {
                if (w.getSection().getUsable() == true) {
                    if (w.getObjectiv().getShip().getHp() > 0) {
                        return "Fire Accepted";
                    }else{
                        return "Ship Defeat";
                    }
                }
            }
        }
        return "Section unusable";
    }
}
