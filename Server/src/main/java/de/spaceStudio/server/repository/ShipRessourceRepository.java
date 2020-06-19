package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.ShipRessource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRessourceRepository extends JpaRepository<ShipRessource, Integer>{
}

