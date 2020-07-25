package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Miguel Caceres 09.05.2020
 */

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
	Optional<Player> findByName(String name);
}
