package de.bremen.server.repository;

import de.bremen.server.model.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;

@Repository
public interface RessourceRepository  extends JpaRepository<Ressource, Integer> {
}
