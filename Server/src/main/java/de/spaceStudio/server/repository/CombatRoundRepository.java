package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.CombatRound;
import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CombatRoundRepository extends JpaRepository<CombatRound, Integer> {

    Optional<List<CombatRound>> findByCrewMembers(CrewMember crewMember);
    Optional<List<CombatRound>> findByWeaponsWhichHaveAttacked(Weapon weapon);
}
