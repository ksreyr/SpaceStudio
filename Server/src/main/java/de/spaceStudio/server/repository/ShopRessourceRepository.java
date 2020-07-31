package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.ShopRessource;
import de.spaceStudio.server.model.Station;
import de.spaceStudio.server.model.StopAbstract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRessourceRepository extends JpaRepository<ShopRessource, Integer> {
    Optional<List<ShopRessource>> findByStation(StopAbstract stopAbstract);
}
