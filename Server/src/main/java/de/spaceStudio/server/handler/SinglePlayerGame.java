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

    public Ship getPlayerShip() {
        return playerShip;
    }

    public void setPlayerShip(Ship playerShip) {
        this.playerShip = playerShip;
    }

    public Ship getShipGegner() {
        return shipGegner;
    }

    public void setShipGegner(Ship shipGegner) {
        this.shipGegner = shipGegner;
    }

    private Ship shipGegner;

    private String difficult;

    private Ship playerShip;


    private StopAbstract stopAbstractShip;

    private Section shipSection;

    private Weapon weapon;

    private ShipRessource shipRessource;

    private CrewMember crewMember;

    private AI ai;

    private String lastScreen;


}
