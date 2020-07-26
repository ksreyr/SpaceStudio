package de.spaceStudio.server.controller;

import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.Player;
import org.springframework.web.bind.annotation.*;

public interface GameController {
    @RequestMapping(value = "/game/start/single-player/{playerName}", method = RequestMethod.POST)
    @ResponseBody
    String initSinglePlayerGame(@PathVariable("playerName") String playerName, @RequestBody SinglePlayerGame singlePlayerGame);

    @RequestMapping(value = "/game/destroy/single-player/{playerName}", method = RequestMethod.GET)
    @ResponseBody
    String destroySinglePlayerGameSession(@PathVariable("playerName") String playerName);

    @RequestMapping(value = "/game/destroy/multiplayer/{sessionID}", method = RequestMethod.GET)
    @ResponseBody
    String destroyMultiPlayerGameSession(@PathVariable("sessionID") String sessionID);

    @RequestMapping(value = "/game/sessions/single-player", method = RequestMethod.GET)
@ResponseBody
    String getAllSinglePlayerSessions();

    @RequestMapping(value = "/game/multiplayer/synchronize/{gameSession}", method = RequestMethod.GET)
    @ResponseBody
    String synchroMultiPlayer(@PathVariable("gameSession") String gameSession);

    @RequestMapping(value = "/game/sessions/multiplayer", method = RequestMethod.GET)
    @ResponseBody
    String getAllMultiPlayerSessions();

    @RequestMapping(value = "/game/start/multiplayer", method = RequestMethod.POST)
    @ResponseBody
    String initMultiPlayer(@RequestBody Player player);

    @RequestMapping(value = "/game/multiplayer/room", method = RequestMethod.GET)
    @ResponseBody
    String checkMultiPlayerRoom();

    @RequestMapping(value = "/game/multiplayer/{sessionID}", method = RequestMethod.POST)
    @ResponseBody
    String joinMultiplayerSession(@PathVariable("sessionID") String gameSession, @RequestBody Player player);

    String destroyMultiPlayerSession();

    @RequestMapping(value = "/game/save/{playerName}", method = RequestMethod.POST)
    @ResponseBody
    String saveGame(@PathVariable("playerName") String playerName, @RequestBody SinglePlayerGame singlePlayerGame);

    @RequestMapping(value = "/game/load/{playerName}", method = RequestMethod.GET)
    @ResponseBody
    SinglePlayerGame loadGame(@PathVariable("playerName") String playerName);

    @RequestMapping(value = "/game/get/single-player/{playerName}", method = RequestMethod.GET)
    @ResponseBody
    SinglePlayerGame getSinglePlayerGame(@PathVariable("playerName") String playerName);

    @RequestMapping(value = "/game/multiplayer/unjoin", method = RequestMethod.POST)
    void unjoinMultiPlayerUser(@RequestBody Player player);
}
