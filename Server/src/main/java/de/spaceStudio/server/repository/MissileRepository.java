package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Missile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissileRepository extends JpaRepository<Missile, Integer> {
}
