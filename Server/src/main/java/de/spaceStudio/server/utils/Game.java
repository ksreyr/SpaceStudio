package de.spaceStudio.server.utils;

import de.spaceStudio.server.model.Player;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Miguel Caceres
 */
public class Game {

    private int gameID;
    private List<Player> players;
    private Timestamp date;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
