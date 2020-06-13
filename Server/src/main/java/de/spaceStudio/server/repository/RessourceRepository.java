package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RessourceRepository  extends JpaRepository<Ressource, Integer> {
}
