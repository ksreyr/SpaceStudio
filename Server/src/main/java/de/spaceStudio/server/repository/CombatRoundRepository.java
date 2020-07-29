package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Actor;
import de.spaceStudio.server.model.CombatRound;
import de.spaceStudio.server.model.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CombatRoundRepository extends JpaRepository<CombatRound, Integer> {

}