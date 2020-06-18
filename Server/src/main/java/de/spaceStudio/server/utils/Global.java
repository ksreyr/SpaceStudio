package de.spaceStudio.server.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains global variables, they can be access in the whole project
 */
public class Global {
    /**
     * Stores all logged users
     */
    public static Set<String> userLogged = new HashSet<>();
}
