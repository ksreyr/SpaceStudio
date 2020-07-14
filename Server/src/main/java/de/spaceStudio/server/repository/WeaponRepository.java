package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Integer> {
    Optional<List<Weapon>> findBySection(Section section);
}
