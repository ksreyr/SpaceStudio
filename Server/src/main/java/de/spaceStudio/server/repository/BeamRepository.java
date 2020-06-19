package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Beam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeamRepository extends JpaRepository<Beam, Integer> {
}
