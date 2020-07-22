package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.handler.MultiPlayerGame;
import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.utils.Global;
import de.spaceStudio.server.utils.JSONFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * <h1>Game Controller</h1>
 * The GameController class implements the single player and
 * multi player endpoints to create game sessions in server
 *
 * @author Miguel Caceres
 * Created: 7.7.2020
 * @version 1.0
 */
@RestController
public class GameController {

    /**
     * PlayerRepository Data
     */
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Logger object
     */
    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

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
            LOG.info("Accepting request for player: " + playerName);
            Global.SinglePlayerGameSessions.put(playerName, singlePlayerGame);
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.NOT_ACCEPTABLE.toString();
        }
    }

    /**
     * Remove single player sessions from Server
     *
     * @param playerName
     * @return HTTP status Accepted if success otherwise HTTP status Not Accepted
     */
    @RequestMapping(value = "/game/destroy/single-player/{playerName}", method = RequestMethod.GET)
    @ResponseBody
    public String destroySinglePlayerGameSession(@PathVariable("playerName") String playerName) {
        if (Global.userLogged.contains(playerName) && (Global.SinglePlayerGameSessions.containsKey(playerName))) {
            Global.SinglePlayerGameSessions.remove(playerName);
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.NOT_EXTENDED.toString();
        }
    }

    /**
     * Remove multi player sessions from Server
     *
     * @param sessionID
     * @return HTTP status Accepted if success otherwise HTTP status Not Accepted
     */
    @RequestMapping(value = "/game/destroy/multiplayer/{sessionID}", method = RequestMethod.GET)
    @ResponseBody
    public String destroyMultiPlayerGameSession(@PathVariable("sessionID") String sessionID) {
        if (Global.MultiPlayerGameSessions.containsKey(sessionID)) {
            Global.MultiPlayerGameSessions.remove(sessionID);
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.NOT_EXTENDED.toString();
        }
    }

        /**
         * Show all active single player game sessions
         *
         * @return JSON
         */
    @RequestMapping(value = "/game/sessions/single-player", method = RequestMethod.GET)
    @ResponseBody
    public String getAllSinglePlayerSessions() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(Global.SinglePlayerGameSessions);
        LOG.info(jsonString);
        return jsonString;
    }

    @RequestMapping(value = "/game/multiplayer/synchronize/{gameSession}", method = RequestMethod.GET)
    @ResponseBody
    public String synchroMultiPlayer(@PathVariable("sessionID") String gameSession){
        MultiPlayerGame multiPlayerGame = Global.MultiPlayerGameSessions.get(gameSession);
        if(multiPlayerGame.getPlayerOne() != null && multiPlayerGame.getPlayerTwo() != null){
            return "true";
        }
    return "false";
    }

    /**
     * Show all active multiplayer game sessions
     *
     * @return JSON
     */
    @RequestMapping(value = "/game/sessions/multiplayer", method = RequestMethod.GET)
    @ResponseBody
    public String getAllMultiPlayerSessions() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(Global.MultiPlayerGameSessions);
        LOG.info(jsonString);
        return jsonString;
    }


    /**
     * @return
     */
    @RequestMapping(value = "/game/start/multiplayer", method = RequestMethod.POST)
    @ResponseBody
    public String initMultiPlayer(@RequestBody Player player) {
        if (Global.userLogged.contains(player.getName())) {
            MultiPlayerGame mult = new MultiPlayerGame();
            mult.setPlayerOne(player);
            Global.usersMultiPlayer.add(player.getName());
            if (Global.MultiPlayerGameSessions.isEmpty()) {
                Global.MultiPlayerGameSessions.put(UUID.randomUUID().toString(), mult);
            }
            return HttpStatus.ACCEPTED.toString();
            /*
            int counter = 0;

            for (Map.Entry<String, MultiPlayerGame> findMap : Global.MultiPlayerGameSessions.entrySet()) {
                if (!findMap.getValue().getPlayerOne().equals(mult.getPlayerOne()) || !findMap.getValue().getPlayerTwo().equals(mult.getPlayerTwo())) {
                    if (counter < 1) {
                        Global.MultiPlayerGameSessions.put(UUID.randomUUID().toString(), mult);
                        counter++;
                    }
                }
            }
            if (counter == 1) {
                return HttpStatus.ACCEPTED.toString();
            }
            */
        }
        return HttpStatus.NOT_ACCEPTABLE.toString();
    }

    /**
     * This method is used to get the session room to join other players
     *
     * @return String This returns the UUID to find current session
     */
    @RequestMapping(value = "/game/multiplayer/room", method = RequestMethod.GET)
    @ResponseBody
    public String checkMultiPlayerRoom() {
        if (!Global.MultiPlayerGameSessions.isEmpty()) {
            Map.Entry<String, MultiPlayerGame> entry = Global.MultiPlayerGameSessions.entrySet().iterator().next();
            return entry.getKey();
        } else {
            return "";
        }
    }

    /**
     * @param gameSession
     * @param player
     * @return
     */
    @RequestMapping(value = "/game/multiplayer/{sessionID}", method = RequestMethod.POST)
    @ResponseBody
    public String joinMultiplayerSession(@PathVariable("sessionID") String gameSession, @RequestBody Player player) {
        if (gameSession != null || !gameSession.isEmpty()) {
            MultiPlayerGame mult = Global.MultiPlayerGameSessions.get(gameSession);
            if (mult != null) {
                mult.setPlayerTwo(player);
                Global.MultiPlayerGameSessions.replace(gameSession, mult);
                return HttpStatus.ACCEPTED.toString();
            }
        }
        return "";
    }

    public String destroyMultiPlayerSession() {
        return null;
    }

    /**
     * Saves single game in Server
     *
     * @param playerName
     * @param singlePlayerGame
     * @return HTTP ACCEPTED Status if success, otherwise HTTP NOT ACCEPTED status
     */
    @RequestMapping(value = "/game/save/{playerName}", method = RequestMethod.POST)
    @ResponseBody
    public String saveGame(@PathVariable("playerName") String playerName, @RequestBody SinglePlayerGame singlePlayerGame) {
        if (Global.userLogged.contains(playerName) && Global.SinglePlayerGameSessions.get(playerName) != null) {
            LOG.info("Accepting save request for player: " + playerName);
            SinglePlayerGame sg = singlePlayerGame;//Global.SinglePlayerGameSessions.get(playerName);
            Optional<Player> fetchPlayer = playerRepository.findByName(playerName);
            if (fetchPlayer != null && fetchPlayer.isPresent()) {
                Player playerToUpdate = fetchPlayer.get();
                // Overwriting JSON file
                if (playerToUpdate.getSavedGame() != null) {
                    JSONFile.cleanJSONSinglePlayerGame(playerToUpdate.getSavedGame());
                    LOG.info("Overwriting save file");
                }
                String storeGamePath = JSONFile.exportJSON(sg);
                playerToUpdate.setSavedGame(storeGamePath);
                playerRepository.save(playerToUpdate);
                LOG.info("Success stored: " + storeGamePath);
            }
            return HttpStatus.ACCEPTED.toString();
        } else {
            return HttpStatus.NOT_ACCEPTABLE.toString();
        }

    }

    /**
     * Load the single player game
     * Extern JSON will loaded
     *
     * @param playerName
     * @return SinglePlayer object
     */
    @RequestMapping(value = "/game/load/{playerName}", method = RequestMethod.GET)
    @ResponseBody
    public SinglePlayerGame loadGame(@PathVariable("playerName") String playerName) {
        if (Global.userLogged.contains(playerName)) {
            Optional<Player> fetchPlayer = playerRepository.findByName(playerName);
            if (fetchPlayer.isPresent()) {
                Player loadPlayerData = fetchPlayer.get();
                SinglePlayerGame sg = JSONFile.importJSONSinglePlayerGame(loadPlayerData.getSavedGame());
                Global.SinglePlayerGameSessions.put(loadPlayerData.getName(), sg);
                LOG.info("Game successful loaded: " + loadPlayerData.getName());
                return sg;
            }
        }
        return null;
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
        LOG.info("getting single player data for player " + playerName);
        return Global.SinglePlayerGameSessions.get(playerName);
    }


    /**
     * This function is temporal in use to logout user from game
     *
     * @return
     */
    @RequestMapping(value = "/game/multiplayer/unjoin", method = RequestMethod.POST)
    public void unjoinMultiPlayerUser(@RequestBody Player player) {
        Global.usersMultiPlayer.remove(player.getName());
    }

}
