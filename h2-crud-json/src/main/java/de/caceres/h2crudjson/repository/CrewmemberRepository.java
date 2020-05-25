package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Crewmember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewmemberRepository extends JpaRepository<Crewmember, Integer> {
}
