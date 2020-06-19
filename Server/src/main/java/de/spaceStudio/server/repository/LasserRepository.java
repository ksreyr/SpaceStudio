package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Lasser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LasserRepository extends JpaRepository<Lasser, Integer> {
}
