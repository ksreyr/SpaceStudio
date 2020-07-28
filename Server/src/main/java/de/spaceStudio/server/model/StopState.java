package de.spaceStudio.server.model;

public enum StopState {
    EXPLORING("Exploring"), JUMPING("Jumping");

    private final String state;

    StopState(String state) {
        this.state = state;
    }
}
