package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ActorControllerImpl implements ActorController {


    @Autowired
    ActorRepository actorRepository;

    @Autowired
    ActorStateRepository actorStateRepository;

    @Autowired
    SectionController sectionController;

    @Autowired
    ShipRepository shipRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    GameController gameController;

    @Autowired
    StopAbstractRepository stopAbstractRepository;

    @Override
    public Actor getActor(Integer id) {
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isPresent()) {
            return actor.get();
        } else{
            throw new IllegalStateException(String.format("Actor %s does not exist", id));
        }
    }

    @Override
    public Actor updateActor(Actor actor) {
        Optional<Actor> fetchActor = actorRepository.findById(actor.getId());
        if (fetchActor.isPresent()) {
            if (!actor.getState().equals(fetchActor.get().getState()))
                gameController.setFightState(actor);

            if (fetchActor.get().getState().getFightState().equals(FightState.WAITING_FOR_TURN)) {
                Optional<Ship> ship = shipRepository.findByOwner(actor);
                if (ship.isPresent()) {
                    Optional<List<Section>> secs = sectionRepository.findAllByShip(ship.get());
                    secs.ifPresent(sections -> sectionController.makeChanges(sections));
                }
            }

            return actorRepository.findById(fetchActor.get().getId()).get();
        } else throw new IllegalStateException(String.format("The Actor %s to update does not exist", actor.getName()));
    }

    @Override
    public List<Actor> findAllByUniverse(Universe universe) {
        Optional<List<StopAbstract>> stops = stopAbstractRepository.findByUniverse(universe);
        List<Actor> actors = new ArrayList<>();
        List<Ship> ships = new ArrayList<>();

        if (stops.isPresent()) {
            for (StopAbstract a :
                    stops.get()) {
                ships.addAll(a.getShips());
            }
            for (Ship s :
                    ships) {
                actors.add(s.getOwner());
            }
        }

        return actors;
    }
}
