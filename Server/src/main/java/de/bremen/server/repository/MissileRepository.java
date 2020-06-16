package de.bremen.server.repository;

import de.bremen.server.model.Missile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissileRepository extends JpaRepository<Missile, Integer> {
}
