package de.bremen.server.repository;

import de.bremen.server.model.ShopRessource;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;

@Repository
public interface ShopRessourceRepository extends JpaRepository<ShopRessource, Integer> {
}
