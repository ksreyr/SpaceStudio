package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Universe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniverseRepository extends JpaRepository<Universe, Integer> {
}
