package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Universe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniverseRepository extends JpaRepository<Universe, Integer> {
}
