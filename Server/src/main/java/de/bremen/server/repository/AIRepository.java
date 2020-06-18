package de.bremen.server.repository;

import de.bremen.server.model.AI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIRepository extends JpaRepository<AI, Integer>{

}
