package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Actor;
import de.spaceStudio.server.repository.ActorRepository;
import de.spaceStudio.server.repository.ActorStateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ActorControllerImpl implements ActorController {


    @Autowired
    ActorRepository actorRepository;

    @Autowired
    ActorStateRepository actorStateRepository;

    @Autowired
    GameController gameController;

    @Override
    public Actor getActor(Integer id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {
            return actor.get();
        } else throw new IllegalStateException(String.format("Actor %s does not exist", id));
    }

    @Override
    public Actor updateActor(Actor actor) {
        Optional<Actor> fetchActor = actorRepository.findById(actor.getId());
        if (fetchActor.isPresent()) {
            if (!actor.getState().equals(fetchActor.get().getState()))
            gameController.setFightState(fetchActor.get());
            return actorRepository.findById(fetchActor.get().getId()).get();
        } else throw new IllegalStateException(String.format("The Actor %s to update does not exist", actor.getName()));
    }
}
