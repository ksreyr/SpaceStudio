package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.CrewMember;
import de.spaceStudio.server.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CrewMemberRepository extends JpaRepository<CrewMember, Integer> {
    void deleteByCurrentSection(Section section);
    Optional<ArrayList<CrewMember>> findAllByCurrentSection(Section section);
}
