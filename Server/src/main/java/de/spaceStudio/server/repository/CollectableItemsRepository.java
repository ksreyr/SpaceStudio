package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.CollectableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectableItemsRepository extends JpaRepository<CollectableItem, Integer> {
}
