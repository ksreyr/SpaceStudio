package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Actor;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
    Optional<Ship> findShipByName(String name);
    Optional<Ship> findShipByNameAndOwner(String name, Actor actor);
    Optional<Ship> findByOwner(Actor player);
}
