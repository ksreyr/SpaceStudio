package de.caceres.h2crudjson.controller;

import de.caceres.h2crudjson.model.Player;
import de.caceres.h2crudjson.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Miguel Caceres 09.05.2020
 */
@RestController
public class PlayerControllerImpl implements de.caceres.h2crudjson.controller.PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * This function is temporal in use to test client to Server connection
	 * Login user if exists
	 * 
	 * @param player which should be loged in
	 * @return true if exists else false
	 */
	@Override
	@RequestMapping(value = "/player/login", method = RequestMethod.POST)
	public String loginUser(@RequestBody Player player) {
		Optional<Player> fetchPlayer = playerRepository.findByName(player.getName());
		if (fetchPlayer.isPresent()) {
			return Boolean.toString(((fetchPlayer.get().getName().equals(player.getName())
					&& fetchPlayer.get().getPassword().equals(player.getPassword()))));
		}
		return "false";

	}

	/**
	 * Get all players from db
	 */
	@Override
	@RequestMapping(value = "/players", method = RequestMethod.GET)
	public List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	/**
	 * Get one player by Id
	 * 
	 * @param id of the player
	 */
	@Override
	@RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
	public Player getPlayer(@PathVariable Integer id) {
		return playerRepository.findById(id).get();
	}

	/**
	 * Creates a new player from JSON player object
	 */
	@Override
	@RequestMapping(value = "/player", method = RequestMethod.POST)
	public String addPlayer(@RequestBody Player player) {
		// For the future hash password
		// player.setPassword(hashPassword(player.getPassword()));
		Player savedPlayer = playerRepository.save(player);
		return HttpStatus.CREATED.toString();
	}

	/**
	 * Update data
	 */
	@Override
	@RequestMapping(value = "/player", method = RequestMethod.PUT)
	public Player updatePlayer(@RequestBody Player player) {
		Player updatedPlayer = playerRepository.save(player);
		return updatedPlayer;
	}

	/**
	 * Delete player by Id
	 */
	@Override
	@RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
	public String deletePlayerById(@PathVariable Integer id) {
		playerRepository.deleteById(id);
		return HttpStatus.ACCEPTED.toString();
	}

	/**
	 * Delete all players
	 */
	@Override
	@RequestMapping(value = "/players", method = RequestMethod.DELETE)
	public String deleteAllPlayers() {
		playerRepository.deleteAll();
		return HttpStatus.OK.toString();
	}

	/**
	 * Salt the password
	 */
	@Override
	public String hashPassword(String weakPassword) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = digest.digest(weakPassword.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(hash);
	}

}
