package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, Integer> {
}
