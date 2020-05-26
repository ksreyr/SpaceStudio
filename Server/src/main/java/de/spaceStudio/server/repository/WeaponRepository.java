package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, Integer> {
}
