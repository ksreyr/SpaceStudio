package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.CombatRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CombatRoundRepository extends JpaRepository<CombatRound, Integer> {

}
