package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Section;
import de.spaceStudio.server.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionControllerImpl implements SectionController {
    @Autowired
    private SectionRepository repository;


    /**
     * Get all sections from db
     *
     * @return List of all sections
     */
    @Override
    public List<Section> getAllSections() {
        return (List<Section>) repository.findAll();
    }

    /**
     * Get one section by Id
     *
     * @param id of the section
     * @return the Section
     */
    @Override
    public Section getSection(Integer id) {
        return null;
    }

    /**
     * Creates a new section from JSON section object
     *
     * @param section the section to be created, which is serialised from the POST JSON
     * @return the serialised Section
     */
    @Override
    public String addSection(Section section) {
        repository.save(section);
        return section.toString();
    }

    /**
     * Update data of the section
     *
     * @param section the section to be updated, which is serialised from the POST JSON
     * @return the updated Section
     */
    @Override
    public Section updateSection(Section section) {
        return null;
    }

    /**
     * Delete section by Id
     *
     * @param id of the section
     * @return JSON of the delted Section
     */
    @Override
    public String deleteSectionById(Integer id) {
        return null;
    }

    /**
     * Delete all sections
     *
     * @return JSON of deleted section
     */
    @Override
    public String deleteAllSections() {
        return null;
    }
}
