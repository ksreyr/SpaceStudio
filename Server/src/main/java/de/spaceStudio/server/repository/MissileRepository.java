package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Missile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MissileRepository extends JpaRepository<Missile, Integer> {
}
