package de.caceres.h2crudjson.controller;

import de.caceres.h2crudjson.model.Player;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface PlayerController {
    @RequestMapping(value = "/player/login", method = RequestMethod.POST)
    String loginUser(@RequestBody Player player);

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    List<Player> getAllPlayers();

    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    Player getPlayer(@PathVariable Integer id);

    @RequestMapping(value = "/player", method = RequestMethod.POST)
    String addPlayer(@RequestBody Player player);

    @RequestMapping(value = "/player", method = RequestMethod.PUT)
    Player updatePlayer(@RequestBody Player player);

    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
    String deletePlayerById(@PathVariable Integer id);

    @RequestMapping(value = "/players", method = RequestMethod.DELETE)
    String deleteAllPlayers();

    String hashPassword(String weakPassword);
}
