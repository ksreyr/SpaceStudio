package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.CollectableItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectableItemsRepository extends JpaRepository<CollectableItem, Integer> {
    Optional<CollectableItem> findByName(String name);
}
