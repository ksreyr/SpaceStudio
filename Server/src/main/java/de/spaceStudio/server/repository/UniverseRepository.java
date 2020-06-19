package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Universe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UniverseRepository extends JpaRepository<Universe, Integer> {
}
