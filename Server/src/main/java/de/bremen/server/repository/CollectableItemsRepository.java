package de.bremen.server.repository;

import de.bremen.server.model.CollectableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectableItemsRepository extends JpaRepository<CollectableItem, Integer> {
}
