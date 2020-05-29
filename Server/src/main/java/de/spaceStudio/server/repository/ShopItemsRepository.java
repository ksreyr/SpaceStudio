package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopItemsRepository extends JpaRepository<ShopItem, Integer> {
}
