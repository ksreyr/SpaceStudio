package de.caceres.h2crudjson.service;

import de.caceres.h2crudjson.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Miguel Caceres
 * 09.05.2020
 */
public interface PlayerService extends JpaRepository<Player, Integer> {
}
