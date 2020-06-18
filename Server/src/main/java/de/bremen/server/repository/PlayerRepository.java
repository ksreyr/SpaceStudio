package de.bremen.server.repository;

import de.bremen.server.model.Player;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Miguel Caceres 09.05.2020
 */
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	Optional<Player> findByName(String name);
}
