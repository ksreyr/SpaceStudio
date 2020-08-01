package de.spaceStudio.server.handler;

import de.spaceStudio.server.model.Actor;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Ship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiPlayerGame implements Serializable {

    private List<Actor> players;  // Is the Player jumping??
    private String universe;
    private Player playerOne;
    private Player playerTwo;
    private Ship shipPlayerOne;
    private Ship shipPlayerTwo;


    public String getUniverse() {
        return universe;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
        this.players = new ArrayList<>();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Ship getShipPlayerOne() {
        return shipPlayerOne;
    }

    public void setShipPlayerOne(Ship shipPlayerOne) {
        this.shipPlayerOne = shipPlayerOne;
    }

    public Ship getShipPlayerTwo() {
        return shipPlayerTwo;
    }

    public void setShipPlayerTwo(Ship shipPlayerTwo) {
        this.shipPlayerTwo = shipPlayerTwo;
    }

    public List<Actor> getPlayers() {
        return players;
    }

    public void setPlayers(List<Actor> players) {
        this.players = players;
    }
}
