package de.bremen.server.controller;

import de.bremen.server.model.Section;
import de.bremen.server.model.Ship;
import de.bremen.server.model.Weapon;

public interface WeaponController extends SectionController {


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
}
