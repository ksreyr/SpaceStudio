package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<Drive, Integer> {
}
