package de.spaceStudio.server.utils;

import com.google.gson.Gson;

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
}
