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

    private String difficult;

    private Ship ship;

    private StopAbstract stopAbstractShip;

    private Section shipSection;

    private Weapon weapon;

    private ShipRessource shipRessource;

    private CrewMember crewMember;






}
