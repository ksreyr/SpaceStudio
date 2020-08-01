package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PlanetRepository extends JpaRepository<Planet, Integer> {
    Optional<Planet> findByName(String name);

}
