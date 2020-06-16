package de.bremen.server.repository;

import de.bremen.server.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<Drive, Integer> {
}
