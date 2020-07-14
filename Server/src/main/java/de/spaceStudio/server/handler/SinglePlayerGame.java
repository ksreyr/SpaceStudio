package de.spaceStudio.server.handler;

import de.spaceStudio.server.model.*;

import java.io.Serializable;

public class SinglePlayerGame implements Serializable {

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public StopAbstract getStopAbstractShip() {
        return stopAbstractShip;
    }

    public void setStopAbstractShip(StopAbstract stopAbstractShip) {
        this.stopAbstractShip = stopAbstractShip;
    }

    public Section getShipSection() {
        return shipSection;
    }

    public void setShipSection(Section shipSection) {
        this.shipSection = shipSection;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public ShipRessource getShipRessource() {
        return shipRessource;
    }

    public void setShipRessource(ShipRessource shipRessource) {
        this.shipRessource = shipRessource;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public String getLastScreen() {
        return lastScreen;
    }

    public void setLastScreen(String lastScreen) {
        this.lastScreen = lastScreen;
    }

    private String difficult;

    private Ship ship;

    private StopAbstract stopAbstractShip;

    private Section shipSection;

    private Weapon weapon;

    private ShipRessource shipRessource;

    private CrewMember crewMember;

    private AI ai;

    private String lastScreen;


}
