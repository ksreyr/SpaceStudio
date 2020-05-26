package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {
}
