package de.bremen.server.repository;

import de.bremen.server.model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RessourceRepository  extends JpaRepository<Ressource, Integer> {
}
