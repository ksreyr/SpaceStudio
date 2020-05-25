package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Player;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Miguel Caceres 09.05.2020
 */
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	Optional<Player> findByName(String name);
}
