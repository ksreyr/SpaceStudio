package de.bremen.server.controller;

import de.bremen.server.model.Player;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface PlayerController {

    /**
     * This function is temporal in use to test client to Server connection
     * Login user if exists
     *
     * @param player which should be loged in
     * @return true if exists else false
     */
    @RequestMapping(value = "/player/login", method = RequestMethod.POST)
    String loginUser(@RequestBody Player player);


    /**
     * Get all players from db
     *
     * @return List of all players
     */
    @RequestMapping(value = "/players", method = RequestMethod.GET)
    List<Player> getAllPlayers();


    /**
     * Get one player by Id
     *
     * @param id of the player
     * @return the Player
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    Player getPlayer(@PathVariable Integer id);


    /**
     * Creates a new player from JSON player object
     *
     * @param player the player to be created, which is serialised from the POST JSON
     * @return the serialised Player
     */
    @RequestMapping(value = "/player", method = RequestMethod.POST)
    String addPlayer(@RequestBody Player player);


    /**
     * Update data of the player
     *
     * @param player the player to be updated, which is serialised from the POST JSON
     * @return the updated Player
     */
    @RequestMapping(value = "/player", method = RequestMethod.PUT)
    Player updatePlayer(@RequestBody Player player);


    /**
     * Delete player by Id
     *
     * @param id of the player
     * @return JSON of the delted Player
     */
    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
    String deletePlayerById(@PathVariable Integer id);


    /**
     * Delete all players
     *
     * @return JSON of deleted player
     */
    @RequestMapping(value = "/players", method = RequestMethod.DELETE)
    String deleteAllPlayers();

    /**
     * Get all logged players name
     * @return
     */
    @RequestMapping(value = "/player/logged-players", method = RequestMethod.GET)
    List<String> getLoggedPlayers();

    /**
     * This function is temporal in use to logout user from game
     * @return
     */
    @RequestMapping(value = "/player/logout", method = RequestMethod.POST)
    void logoutUser(@RequestBody Player player);

    String hashPassword(String weakPassword);
}
