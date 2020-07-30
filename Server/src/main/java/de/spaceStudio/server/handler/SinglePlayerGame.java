package de.spaceStudio.server.handler;

import de.spaceStudio.server.model.*;

import java.io.Serializable;

public class SinglePlayerGame implements Serializable {

    private Ship shipGegner;
    private String difficult;
    private Ship playerShip;
    private StopAbstract stopAbstractShip;
    private Section shipSection1;
    private Section shipSection2;
    private Section shipSection3;
    private Section shipSection4;
    private Section shipSection5;
    private Section shipSection6;
    
    private Weapon weapon;
    private ShipRessource shipRessource;
    private CrewMember crewMember;
    private AI ai;
    private String lastScreen;

    public Section getShipSection1() {
        return shipSection1;
    }

    public void setShipSection1(Section shipSection1) {
        this.shipSection1 = shipSection1;
    }


    public Section getShipSection2() {
        return shipSection2;
    }

    public void setShipSection2(Section shipSection2) {
        this.shipSection2 = shipSection2;
    }

    public Section getShipSection3() {
        return shipSection3;
    }

    public void setShipSection3(Section shipSection3) {
        this.shipSection3 = shipSection3;
    }

    public Section getShipSection4() {
        return shipSection4;
    }

    public void setShipSection4(Section shipSection4) {
        this.shipSection4 = shipSection4;
    }

    public Section getShipSection5() {
        return shipSection5;
    }

    public void setShipSection5(Section shipSection5) {
        this.shipSection5 = shipSection5;
    }

    public Section getShipSection6() {
        return shipSection6;
    }

    public void setShipSection6(Section shipSection6) {
        this.shipSection6 = shipSection6;
    }

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


}
