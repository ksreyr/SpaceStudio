package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Integer> {
}
