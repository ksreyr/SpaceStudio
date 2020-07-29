package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RessourceRepository extends JpaRepository<Ressource, Integer> {
}
