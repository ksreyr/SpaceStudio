package de.spaceStudio.server.repository;

import de.spaceStudio.server.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    /**
     * FInd all the Sections which belong to this ship
     * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-three-custom-queries-with-query-methods/
     * @param shipID
     * @return
     */
    public  List<Section> findByShip(int shipID);

}
