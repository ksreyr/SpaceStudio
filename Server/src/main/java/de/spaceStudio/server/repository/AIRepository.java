package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.AI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIRepository extends JpaRepository<AI, Integer>{
    Optional<AI> findByName(String name);
}
