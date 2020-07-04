package de.spaceStudio.server.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

/**
 * @author Miguel Caceres
 */
public class JSONFile {

    protected static UUID uuid = UUID.randomUUID();
    /**
     * File to store
     */
    private static String FILE_NAME = "memory/saved-game" + uuid.toString() + ".json";

    /**
     * exports game to json file
     *
     * @param gameToExport
     */
    public static String exportJSON(Game gameToExport) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(gameToExport, writer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return FILE_NAME;
    }

    /**
     * Imports the json file
     *
     * @param path to sored file
     * @return
     */
    public static Game importJSON(String path) {
        Gson gson = new Gson();
        Game loadedGame = new Game();
        try {
            JsonReader jsonFile = new JsonReader(new FileReader(path));
            loadedGame = gson.fromJson(jsonFile, Game.class);
            jsonFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadedGame;
    }
}
