package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer> {
}
