package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer> {
}
