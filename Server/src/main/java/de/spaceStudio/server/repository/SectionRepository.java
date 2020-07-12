package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    /**
     * FInd all the Sections which belong to this ship
     * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-three-custom-queries-with-query-methods/
     * @param ship
     * @return
     */
      Optional<List<Section>> findAllByShip(Ship ship);
      void deleteByShip(Ship ship);


}
