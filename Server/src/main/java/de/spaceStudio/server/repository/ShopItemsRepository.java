package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.ShopRessource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopItemsRepository extends JpaRepository<ShopRessource, Integer> {
}
