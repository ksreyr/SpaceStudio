package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface WeaponController  {
    /**
     * Will the planned Attack Dammage the Ship
     *
     * @param w is the weapon
     * @param s is the Ship which should be attacked
     * @return if the attack is possible
     */
    boolean canAttack(Weapon w, Ship s);

    /**
     * Calculate the Dammage and update the Ship to reflect the Attack
     *
     * @param w   is used for attacking
     * @param sec is being attacked
     * @param s   is the ship being attacked
     * @return the Ship after being attacked
     */
    Ship calculateDammage(Weapon w, Section sec, Ship s);

    /**
     * Get all weapons from db
     *
     * @return List of all weapons
     */
    @RequestMapping(value = "/weapons", method = RequestMethod.GET)
    List<Weapon> getAllWeapons();


    /**
     * Get one weapon by Id
     *
     * @param id of the weapon
     * @return the Weapon
     */
    @RequestMapping(value = "/weapon/{id}", method = RequestMethod.GET)
    Weapon getWeapon(@PathVariable Integer id);


    /**
     * Creates a new weapon from JSON weapon object
     *
     * @param weapon the weapon to be created, which is serialised from the POST JSON
     * @return the serialised Weapon
     */
    @RequestMapping(value = "/weapon", method = RequestMethod.POST)
    String addWeapon(@RequestBody Weapon weapon);


    /**
     * Update data of the weapon
     *
     * @param weapon the weapon to be updated, which is serialised from the POST JSON
     * @return the updated Weapon
     */
    @RequestMapping(value = "/weapon", method = RequestMethod.PUT)
    Weapon updateWeapon(@RequestBody Weapon weapon);


    /**
     * Delete weapon by Id
     *
     * @param id of the weapon
     * @return JSON of the delted Weapon
     */
    @RequestMapping(value = "/weapon/{id}", method = RequestMethod.DELETE)
    String deleteWeaponById(@PathVariable Integer id);


    /**
     * Delete all weapons
     *
     * @return JSON of deleted weapon
     */
    @RequestMapping(value = "/weapons", method = RequestMethod.DELETE)
    String deleteAllWeapons();

    @RequestMapping(value = "/fire", method = RequestMethod.POST)
    String fire(@RequestBody Weapon weapon);
}
