package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.ShipRessource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRessourceRepository extends JpaRepository<ShipRessource, Integer>{
    Optional<List<ShipRessource>> findByShip(Ship ship);
}

