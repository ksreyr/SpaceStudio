package de.caceres.h2crudjson.repository;

import de.caceres.h2crudjson.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
