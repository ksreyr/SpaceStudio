package de.spaceStudio.server.repository;

import de.spaceStudio.server.handler.ActorState;
import de.spaceStudio.server.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorStateRepository extends JpaRepository<ActorState, Integer> {
}
