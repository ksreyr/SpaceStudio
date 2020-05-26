package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Section;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface SectionController {
    @RequestMapping(value = "/section/login", method = RequestMethod.POST)
    String loginUser(@RequestBody Section section);

    @RequestMapping(value = "/sections", method = RequestMethod.GET)
    List<Section> getAllSections();

    @RequestMapping(value = "/section/{id}", method = RequestMethod.GET)
    Section getSection(@PathVariable Integer id);

    @RequestMapping(value = "/section", method = RequestMethod.POST)
    String addSection(@RequestBody Section section);

    @RequestMapping(value = "/section", method = RequestMethod.PUT)
    Section updateSection(@RequestBody Section section);

    @RequestMapping(value = "/section/{id}", method = RequestMethod.DELETE)
    String deleteSectionById(@PathVariable Integer id);

    @RequestMapping(value = "/sections", method = RequestMethod.DELETE)
    String deleteAllSections();

    String hashPassword(String weakPassword);
}

