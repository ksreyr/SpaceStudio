package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.StopAbstract;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface StopAbstractController {


    /**
     * Get all stopAbstracts from db
     *
     * @return List of all stopAbstracts
     */
    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.GET)
    List<StopAbstract> getAllStopAbstracts();


    /**
     * Get one stopAbstract by Id
     *
     * @param id of the stopAbstract
     * @return the StopAbstract
     */
    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.GET)
    StopAbstract getStopAbstract(@PathVariable Integer id);


    /**
     * Creates a new stopAbstract from JSON stopAbstract object
     *
     * @param stopAbstract the stopAbstract to be created, which is serialised from the POST JSON
     * @return the serialised StopAbstract
     */
    @RequestMapping(value = "/stopAbstract", method = RequestMethod.POST)
    String addStopAbstract(@RequestBody StopAbstract stopAbstract);


    /**
     * Update data of the stopAbstract
     *
     * @param stopAbstract the stopAbstract to be updated, which is serialised from the POST JSON
     * @return the updated StopAbstract
     */
    @RequestMapping(value = "/stopAbstract", method = RequestMethod.PUT)
    StopAbstract updateStopAbstract(@RequestBody StopAbstract stopAbstract);

    /**
     * Delete stopAbstract by Id
     *
     * @param id of the stopAbstract
     * @return JSON of the delted StopAbstract
     */
    @RequestMapping(value = "/stopAbstract/{id}", method = RequestMethod.DELETE)
    String deleteStopAbstractById(@PathVariable Integer id);

    /**
     * Delete all stopAbstracts
     *
     * @return JSON of deleted stopAbstract
     */
    @RequestMapping(value = "/stopAbstracts", method = RequestMethod.DELETE)
    String deleteAllStopAbstracts();

    @RequestMapping(value = "/makejump", method = RequestMethod.POST)
    String makeJump(@RequestBody List<StopAbstract> stops);

    /**
     * Is is possible to Land?
     * It is possible to Land when booth Players have select a new Station to Jump to
     * @param  player which wants to land
     * @return if they can Land
     */
    @RequestMapping(value = "/canLand", method = RequestMethod.GET)
    Boolean canLand(@RequestBody Player player);

//
//    @PostMapping(value="/hasLanded" )
//    String hasLanded(@RequestBody Player player);

}
