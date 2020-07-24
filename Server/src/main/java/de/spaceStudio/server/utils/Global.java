package de.spaceStudio.server.utils;

import de.spaceStudio.server.handler.MultiPlayerGame;
import de.spaceStudio.server.handler.SinglePlayerGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class contains global variables, they can be access in the whole project
 */
public class Global {
    /**
     * Stores all logged users
     */
    public static Set<String> userLogged = new HashSet<>();


    /**
     * Stores all MultiPlayer users
     */
    public static Set<String> usersMultiPlayer = new HashSet<>();

    /**
     * Handler the single player sessions
     */
    public static Map<String, SinglePlayerGame> SinglePlayerGameSessions = new HashMap<>();

    /**
     * Handler the multi player sessions
     */
    public static Map<String, MultiPlayerGame> MultiPlayerGameSessions = new HashMap<>();

}
