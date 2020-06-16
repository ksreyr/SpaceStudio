package de.bremen.server.controller;

import de.bremen.server.model.AI;

import java.util.List;

public class AIControllerImpl implements AIController {
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
        return null;
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
