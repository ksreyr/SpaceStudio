package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {
}
