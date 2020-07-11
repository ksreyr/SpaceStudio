package de.spaceStudio.server.controller;

import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.utils.Game;
import de.spaceStudio.server.utils.Global;
import de.spaceStudio.server.utils.JSONFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Game Controller
 *
 * @author Miguel Caceres
 * Created: 7.7.2020
 */
@RestController
public class GameController {

    @Autowired
    private PlayerRepository playerRepository;

    Logger logger = LoggerFactory.getLogger(GameController.class);

    /**
     * Init single game session in Server
     *
     * @param playerName
     * @param singlePlayerGame
     * @return HTTP Status
     */
    @RequestMapping(value = "/game/start/single-player/{playerName}", method = RequestMethod.POST)
    @ResponseBody
    public String initSinglePlayerGame(@PathVariable("playerName") String playerName, @RequestBody SinglePlayerGame singlePlayerGame) {
        if (Global.userLogged.contains(playerName)) {
            logger.info("Accepting request for player: " + playerName);
            Global.SinglePlayerGameSessions.put(playerName, singlePlayerGame);
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.NOT_ACCEPTABLE.toString();
        }

    }

    /**
     * Saves single game in Server
     *
     * @param playerName
     * @param singlePlayerGame
     * @return HTTP Status
     */
    @RequestMapping(value = "/game/save/{playerName}", method = RequestMethod.POST)
    @ResponseBody
    public String saveGame(@PathVariable("playerName") String playerName, @RequestBody SinglePlayerGame singlePlayerGame) {
        if (Global.userLogged.contains(playerName) && Global.SinglePlayerGameSessions.get(playerName) != null) {
            logger.info("Accepting save request for player: " + playerName);
            SinglePlayerGame sg = Global.SinglePlayerGameSessions.get(playerName);
            Optional<Player> fetchPlayer = playerRepository.findByName(playerName);
            if (fetchPlayer != null && fetchPlayer.isPresent()) {
                Player playerToUpdate = fetchPlayer.get();
                String storeGamePath = JSONFile.exportJSON(sg);
                playerToUpdate.setSavedGame(storeGamePath);
                playerRepository.save(playerToUpdate);
                logger.info("Success stored: " + storeGamePath);
            }
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.NOT_ACCEPTABLE.toString();
        }

    }

    /**
     * Gets the player's game current status
     *
     * @param playerName
     * @return SinglePlayerGame as JSON
     */
    @RequestMapping(value = "/game/get/single-player/{playerName}", method = RequestMethod.GET)
    @ResponseBody
    public SinglePlayerGame getSinglePlayerGame(@PathVariable("playerName") String playerName) {
        logger.info("getting single player data for player " + playerName);
        return Global.SinglePlayerGameSessions.get(playerName);
    }
}
