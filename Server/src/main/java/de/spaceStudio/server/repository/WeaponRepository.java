package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Integer> {
}
