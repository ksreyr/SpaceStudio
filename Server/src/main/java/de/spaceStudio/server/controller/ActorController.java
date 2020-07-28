package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Actor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

public interface ActorController {



    /**
     * Get one Actor by Id
     *
     * @param id of the Actor
     * @return the Actor
     */
    @RequestMapping(value = "/actor/{id}", method = RequestMethod.GET)
    Actor getActor(@PathVariable Integer id);


    /**
     * Update data of the Actor
     *
     * @param Actor the Actor to be updated, which is serialised from the POST JSON
     * @return the updated Actor
     */
    @RequestMapping(value = "/actor", method = RequestMethod.PUT)
    Actor updateActor(@RequestBody Actor Actor);

}
