package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
