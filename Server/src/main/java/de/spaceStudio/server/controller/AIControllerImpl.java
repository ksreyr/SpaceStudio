package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.ActorState;
import de.spaceStudio.server.model.AI;
import de.spaceStudio.server.model.GameRound;
import de.spaceStudio.server.repository.AIRepository;
import de.spaceStudio.server.repository.ActorStateRepository;
import de.spaceStudio.server.repository.GameRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class AIControllerImpl implements AIController {
    @Autowired
    AIRepository aiRepository;

    @Autowired
    ActorStateRepository actorStateRepository;


    @Autowired
    GameRoundRepository gameRoundRepository;
    /**
     * Get all AIs from db
     *
     * @return List of all AIs
     */
    @Override
    public List<AI> getAllAIs() {
        return null;
    }

    /**
     * Get one AI by Id
     *
     * @param id of the AI
     * @return the AI
     */
    @Override
    public AI getAI(Integer id) {
        return null;
    }

    /**
     * Creates a new AI from JSON AI object
     *
     * @param ai the AI to be created, which is serialised from the POST JSON
     * @return the serialised AI
     */
    @Override
    public String addAI(AI ai) {

        ActorState aa = new ActorState();
        if (ai.getState() == null) {
            actorStateRepository.save(aa);
            ai.setState(aa);
        }
        GameRound gameRound = new GameRound();
        List<GameRound> gameRounds = gameRoundRepository.findByActor(ai);
        if (gameRounds.isEmpty()) {
            gameRound.setActor(ai);
            gameRoundRepository.save(gameRound);
        }
        ai.setState(aa);
        aiRepository.save(ai);
        return HttpStatus.CREATED.toString();
    }

    @RequestMapping(value = "/AIs", method = RequestMethod.POST)
    public String addAI(@RequestBody List<AI> ais) {
        List<AI> aisSaved= new ArrayList<>();
        for (AI ai :
                ais) {
            ActorState aa = new ActorState();
            if (ai.getState() == null) {
                actorStateRepository.save(aa);
                ai.setState(aa);
            }
            ai.setState(aa);
            AI aisaved=aiRepository.save(ai);
            aisSaved.add(aisaved);
        }
        Gson gson= new Gson();
        gson.toJson(aisSaved);
        return gson.toJson(aisSaved);
    }
    /**
     * Update data of the AI
     *
     * @param ai the AI to be updated, which is serialised from the POST JSON
     * @return the updated AI
     */
    @Override
    public AI updateAI(AI ai) {
        return null;
    }

    /**
     * Delete AI by Id
     *
     * @param id of the AI
     * @return JSON of the delted AI
     */
    @Override
    public String deleteAIById(Integer id) {
        return null;
    }

    /**
     * Delete all AIs
     *
     * @return JSON of deleted AI
     */
    @Override
    public String deleteAllAIs() {
        return null;
    }
}
