package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Section;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface SectionController {


    /**
     * Get all sections from db
     *
     * @return List of all sections
     */

    @RequestMapping(value = "/sections", method = RequestMethod.GET)
    List<Section> getAllSections();

    /**
     * Get one section by Id
     *
     * @param id of the section
     * @return the Section
     */
    @RequestMapping(value = "/section/{id}", method = RequestMethod.GET)
    Section getSection(@PathVariable Integer id);

    /**
     * Creates a new section from JSON section object
     *
     * @param section the section to be created, which is serialised from the POST JSON
     * @return the serialised Section
     */
    @RequestMapping(value = "/section", method = RequestMethod.POST)
    String addSection(@RequestBody Section section);

    /**
     * Update data of the section
     *
     * @param section the section to be updated, which is serialised from the POST JSON
     * @return the updated Section
     */
    @RequestMapping(value = "/section", method = RequestMethod.PUT)
    Section updateSection(@RequestBody Section section);

    /**
     * Delete section by Id
     *
     * @param id of the section
     * @return JSON of the delted Section
     */
    @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
    String deleteSectionById(@PathVariable Integer id);

    /**
     * Delete all sections
     *
     * @return JSON of deleted section
     */
    @RequestMapping(value = "/sections", method = RequestMethod.DELETE)
    String deleteAllSections();

    /**
     * Find all Sections for one Ship
     * @param id the Ship
     * @return the Sections
     */
    @RequestMapping(value =  "/ship/{id}/sections", method = RequestMethod.GET)
    List<Section> sectionsByShip(@PathVariable Integer id);
}

