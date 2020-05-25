package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Missile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissileRepository extends JpaRepository<Missile, Integer> {
}
