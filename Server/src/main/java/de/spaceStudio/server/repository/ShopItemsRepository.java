package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopItemsRepository extends JpaRepository<ShopItem, Integer> {
}
