package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Actor;
import de.spaceStudio.server.model.CombatRound;
import de.spaceStudio.server.model.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRoundRepository extends JpaRepository<GameRound, Integer> {
    List<GameRound> findAllByActor(Actor actor);
    Optional<List<GameRound>> findAllByCombatRounds(CombatRound combatRound);
}

