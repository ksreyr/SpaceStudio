package de.spaceStudio.server.model;

public enum StopState {
    EXPLORING("Exploring"), JUMPING("Jumping");

    private String state;

    StopState(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
