package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Universe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UniverseRepository extends JpaRepository<Universe, Integer> {
    Optional<Universe> findByName(String name);
}
