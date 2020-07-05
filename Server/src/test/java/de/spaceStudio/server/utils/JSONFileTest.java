package de.spaceStudio.server.utils;

import de.spaceStudio.server.model.Player;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JSONFileTest {

    private static String filePath;

    @BeforeAll
    public static void setup(){
        Game bugGame = new Game();
        bugGame.setGameID(0);
        List<Player> players = new ArrayList<>();
        players.add(Player.builderPlayer().name("Ninja").buildPlayer());
        players.add(Player.builderPlayer().name("Pirate").buildPlayer());
        bugGame.setPlayers(players);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        bugGame.setDate(currentTimestamp);
        filePath = JSONFile.exportJSON(bugGame);
        assertNotNull(filePath);
    }

    @Test
    public void testExportJSON() {
        Game bugGame = new Game();
        bugGame.setGameID(0);
        List<Player> players = new ArrayList<>();
        players.add(Player.builderPlayer().name("Ninja").buildPlayer());
        players.add(Player.builderPlayer().name("Pirate").buildPlayer());
        bugGame.setPlayers(players);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        bugGame.setDate(currentTimestamp);
        filePath = JSONFile.exportJSON(bugGame);
        assertNotNull(filePath);
        System.out.println(filePath);
    }

    @Test
    public void testImportJSON(){
        Game loadGame = JSONFile.importJSON(filePath);
        assertNotNull(loadGame);
        System.out.println(loadGame.getGameID());
    }

    @AfterAll
    public static void cleanUp() throws FileNotFoundException {
        File file = new File(filePath);
        file.deleteOnExit();
    }


}