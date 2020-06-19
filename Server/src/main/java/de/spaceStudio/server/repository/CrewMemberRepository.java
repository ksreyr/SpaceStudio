package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer> {
}
