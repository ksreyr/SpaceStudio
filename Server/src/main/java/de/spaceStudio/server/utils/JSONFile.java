package de.spaceStudio.server.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import de.spaceStudio.server.handler.SinglePlayerGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

/**
 * @author Miguel Caceres
 */
public class JSONFile {


    public static final Logger logger = LoggerFactory.getLogger(JSONFile.class);

    // protected static UUID uuid = UUID.randomUUID();
    /**
     * File to store
     */
    private static String FILE_NAME = "memory/saved-game";

    /**
     * exports game to json file
     *
     * @param gameToExport
     */
    public static String exportJSON(Game gameToExport) {
        Gson gson = new Gson();
        UUID uuid = UUID.randomUUID();
        String fullPath = FILE_NAME + uuid.toString() + ".json";
        try {
            FileWriter writer = new FileWriter(fullPath);
            gson.toJson(gameToExport, writer);
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fullPath;
    }


    /**
     * exports game to json file singleplayer
     *
     * @param gameToExport
     */
    public static String exportJSON(SinglePlayerGame gameToExport) {
        Gson gson = new Gson();
        UUID uuid = UUID.randomUUID();
        String fullPath = FILE_NAME + uuid.toString() + ".json";
        try {
            FileWriter writer = new FileWriter(fullPath);
            gson.toJson(gameToExport, writer);
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fullPath;
    }

    /**
     * Imports the json file
     *
     * @param path to sored file
     * @return
     */
    public static SinglePlayerGame importJSONSinglePlayerGame(String path) {
        if (path != null) {
            Gson gson = new Gson();
            SinglePlayerGame loadedGame = new SinglePlayerGame();
            try {
                JsonReader jsonFile = new JsonReader(new FileReader(path));
                loadedGame = gson.fromJson(jsonFile, SinglePlayerGame.class);
                jsonFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return loadedGame;
        }
        return null;
    }

    /**
     * Deletes the physical json file
     * <code>True</code> if the file success deleted
     *
     * @param path
     */
    public static boolean cleanJSONSinglePlayerGame(String path) {
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
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
