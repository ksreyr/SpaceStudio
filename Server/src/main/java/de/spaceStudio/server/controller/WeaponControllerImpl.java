package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    @Autowired
    ShipRessourceRepository shipRessourceRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    GameRoundRepository gameRoundRepository;


    @Autowired
    CombatRoundRepository combatRoundRepository;

    private float removeOxygen = 20;
    private int priceWeapon = 30;

    /**
     * Generate a random Number
     *
     * @param min lowest
     * @param max highest
     * @return a number inside the bounds
     */
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

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
    @GetMapping(value = "/ship/{id}/weapons")
    public List<Weapon> getWeapons(@PathVariable Integer id) {
        Optional<Ship> ship = shipRepository.findById(id);
        if (ship.isEmpty()) {
            return new ArrayList<>();
        }
        List<Section> sections = sectionRepository.findAllByShip(ship.get()).get();
        List<Weapon> weapons = new ArrayList<>();

        for (Section s :
                sections) {
            Optional<List<Weapon>> w = weaponRepository.findBySection(s);
            w.ifPresent(weapons::addAll);
        }

        return weapons;
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
        List<Weapon> weaponList = new ArrayList<>();
        for (Weapon w :
                weapons) {
            Weapon weapon = weaponRepository.save(w);
            weaponList.add(weapon);
        }
        Gson gson = new Gson();
        return gson.toJson(weaponList);
    }

    @Override
    @RequestMapping(value = "/weapon", method = RequestMethod.PUT)
    public List<Weapon> updateWeapon(@RequestBody List<Weapon> weapons) {
        for (Weapon e :
                weapons) {
            weaponRepository.save(e);
        }
        return weapons;
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
    public List<Section> fire(@RequestBody @NotEmpty List<Weapon> pWeapons) {
        Random random = new Random();


        List<Weapon> weapons = getWeapons(pWeapons);

        for (Weapon weapon :
                weapons) {
            //Search the objective
            Optional<Ship> ship = shipRepository.findById(weapon.getObjectiv().getShip().getId());
            if (ship.isPresent()) {
                boolean hasHit = ((float)  random.nextDouble() + weapon.getHitRate()) >= 1;  // Treffer falls ueber 50%
                weapon.setCurrentBullets(weapon.getCurrentBullets() - 1);
                Optional<Actor> actor = actorRepository.findById(pWeapons.get(0).getSection().getShip().getOwner().getId());
                if (hasHit && actor.isPresent()) {  // Dont change anything if no hit
                    // Find the last combat Round and add the weapon because it has attacked
                    List<GameRound> gameRounds = gameRoundRepository.findByActor(actor.get());
                    GameRound curentGameRound = gameRounds.get(gameRounds.size() - 1);

                    if (curentGameRound.getCombatRounds().size() == 0) {
                        CombatRound combatRound = new CombatRound();
                        combatRoundRepository.save(combatRound);
                        curentGameRound.getCombatRounds().add(combatRound);
                        gameRoundRepository.save(curentGameRound);
                    }

                    CombatRound currentCombatRound = curentGameRound.getCombatRounds().get(curentGameRound.getCombatRounds().size() - 1);
                    currentCombatRound.getWeaponsWhichHaveAttacked().add(weapon);
                    combatRoundRepository.save(currentCombatRound);
                    if (ship.get().getShield() > 0) {
                        ship.get().setShield(ship.get().getShield() - weapon.getDamage());
                        if (weapon.getObjectiv().getPowerCurrent() > 0) {
                            weapon.getObjectiv().setPowerCurrent(weapon.getObjectiv().getPowerCurrent() - 1);
                        }
                    } else {
                        //Without_Schield
                        ship.get().setHp(ship.get().getHp() - weapon.getDamage());
                        weapon.getObjectiv().setUsable(false);
                        if (weapon.getObjectiv().getOxygen() > 0) {
                            weapon.getObjectiv().setOxygen(weapon.getObjectiv().getOxygen() - removeOxygen);
                        }
                    }
                }
                sectionRepository.save(weapon.getObjectiv());
                shipRepository.save(ship.get());
                weaponRepository.save(weapon);
            }
        }
        Optional<List<Section>> sections = sectionRepository.findAllByShip(pWeapons.get(0).getObjectiv().getShip());
        return sections.orElseGet(ArrayList::new);
//            throw new IllegalStateException(String.format("There is no Ship %s to take Dammage", pWeapons.get(0).getObjectiv().getShip().getId()));
    }

    @Override
    public List<Boolean> shotValidation(List<Weapon> pWeapons) {
        List<Boolean> shots = new ArrayList<>();

        List<Weapon> weapons = getWeapons(pWeapons);
        weapons.forEach(w -> shots.add(canShoot(w)));
        return shots;
    }

    private List<Weapon> getWeapons(List<Weapon> pWeapons) {
        List<Integer> ids = new ArrayList<>();
        pWeapons.forEach(e -> ids.add(e.getId()));
        List<Weapon> weapons = weaponRepository.findAllById(ids);

        weapons.forEach(w -> w.setObjectiv(pWeapons.get(0).getObjectiv()));
        return weapons;
    }

    @Override
    public boolean canShoot(Weapon w) {
        if (w.getObjectiv() != null && w.getObjectiv().getShip() != null && w.getObjectiv().getShip().getId() != null) {
            Ship ship = shipRepository.findById(w.getObjectiv().getShip().getId()).get();
            if (w.getObjectiv() != null && ship.getHp() > 0 && w.getWarmUp() == 0 && w.getCurrentBullets() > 0
                    && w.getSection().getPowerCurrent() >= w.getSection().getPowerRequired()) {
                return w.getSection().getUsable();
            }
        }
        return false;
    }

    @RequestMapping(value = "/buyweapon", method = RequestMethod.POST)
    public String buyWeapon(@RequestBody List<Weapon> weapons) {
        Gson gson = new Gson();
        Section section = new Section();
        for (Weapon weapon :
                weapons) {
            List<ShipRessource> shipRessources = shipRessourceRepository.findByShip(weapon.getSection().getShip()).get();
            for (ShipRessource sr : shipRessources) {
                if (sr.getName().equals(RessourceName.GOLD)) {
                    if (sr.getAmount() - priceWeapon > 0) {
                        sr.setAmount(sr.getAmount() - priceWeapon);
                        shipRessourceRepository.save(sr);
                    } else {
                        return gson.toJson(shipRessources);
                    }
                }
            }
            section = sectionRepository.findById(weapon.getSection().getId()).get();
            weapon.setSection(section);
            weaponRepository.save(weapon);
        }
        List<ShipRessource> shipRessources = shipRessourceRepository.findByShip(section.getShip()).get();
        return gson.toJson(shipRessources);
    }
}
