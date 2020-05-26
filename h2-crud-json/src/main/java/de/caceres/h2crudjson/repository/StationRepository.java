package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Section;
import de.caceres.h2crudjson.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {
}
