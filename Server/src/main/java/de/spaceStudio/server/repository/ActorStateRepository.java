package de.spaceStudio.server.repository;

import de.spaceStudio.server.handler.ActorState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorStateRepository extends JpaRepository<ActorState, Integer> {
}
