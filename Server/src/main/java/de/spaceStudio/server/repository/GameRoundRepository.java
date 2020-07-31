package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Actor;
import de.spaceStudio.server.model.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRoundRepository extends JpaRepository<GameRound, Integer> {
    List<GameRound> findAllByActor(Actor actor);
}

