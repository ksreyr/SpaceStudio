package de.spaceStudio.client.util;

import de.spaceStudio.server.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains global variables, they can be access in the whole project
 *
 * @author Miguel Caceres
 * created on 6.17.2020
 */
public class Global {

    /**
     * Hardcoded server URL
     */
    public static final String SERVER_URL = "http://localhost:8080";

    /**
     * Server player endpoint
     */
    public static final String PLAYER_ENDPOINT = "/player";

    /**
     * Server logged player endpoint
     */
    public static final String PLAYER_LOGGED_ENDPOINT = "/player/logged-players";

    /**
     * Server login endpoint
     */
    public static final String PLAYER_LOGIN_ENDPOINT = "/player/login";

    /**
     * Server logout endpoint
     */
    public static final String PLAYER_LOGOUT_ENDPOINT = "/player/logout";
    /**
     * Ship Cration
     */
    public static final String SHIP_CREATION_ENDPOINT = "/ship";
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Player currentPlayer;

    /**
     * Gets all Users
     */
    public static List<String> playersOnline = new ArrayList<>();
}
