package de.spaceStudio.server.utils;

import de.spaceStudio.server.model.Player;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JSONFileTest {

    @Test
    public void exportJSON() {
        Game bugGame = new Game();
        bugGame.setGameID(0);
        List<Player> players = new ArrayList<>();
        players.add(Player.builderPlayer().name("Ninja").buildPlayer());
        players.add(Player.builderPlayer().name("Pirate").buildPlayer());
        bugGame.setPlayers(players);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        bugGame.setDate(currentTimestamp);
        String result = JSONFile.exportJSON(bugGame);
        assertNotNull(result);
        System.out.println(result);
    }
}