package de.bremen.server.repository;

import de.bremen.server.model.Universe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniverseRepository extends JpaRepository<Universe, Integer> {
}
