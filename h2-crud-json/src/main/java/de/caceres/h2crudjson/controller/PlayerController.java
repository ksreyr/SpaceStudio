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
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * This function is temporal in use to test client -> Server connection
	 * Login user if exists
	 * 
	 * @param player
	 * @return true if exists else false
	 */
	@RequestMapping(value = "/player/login", method = RequestMethod.POST)
	private String loginUser(@RequestBody Player player) {
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
	@RequestMapping(value = "/players", method = RequestMethod.GET)
	private List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	/**
	 * Get one player by Id
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
	private Player getPlayer(@PathVariable Integer id) {
		return playerRepository.findById(id).get();
	}

	/**
	 * Creates a new player from JSON player object
	 */
	@RequestMapping(value = "/player", method = RequestMethod.POST)
	private String addPlayer(@RequestBody Player player) {
		// For the future hash password
		// player.setPassword(hashPassword(player.getPassword()));
		Player savedPlayer = playerRepository.save(player);
		return HttpStatus.CREATED.toString();
	}

	/**
	 * Update data
	 */
	@RequestMapping(value = "/player", method = RequestMethod.PUT)
	private Player updatePlayer(@RequestBody Player player) {
		Player updatedPlayer = playerRepository.save(player);
		return updatedPlayer;
	}

	/**
	 * Delete player by Id
	 */
	@RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
	private String deletePlayerById(@PathVariable Integer id) {
		playerRepository.deleteById(id);
		return HttpStatus.ACCEPTED.toString();
	}

	/**
	 * Delete all players
	 */
	@RequestMapping(value = "/players", method = RequestMethod.DELETE)
	private String deleteAllPlayers() {
		playerRepository.deleteAll();
		return HttpStatus.OK.toString();
	}

	/**
	 * Salt the password
	 */
	protected String hashPassword(String weakPassword) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = digest.digest(weakPassword.getBytes(StandardCharsets.UTF_8));
		return new String(Base64.getEncoder().encodeToString(hash));
	}

}
