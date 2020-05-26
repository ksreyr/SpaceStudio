package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Integer> {
}
