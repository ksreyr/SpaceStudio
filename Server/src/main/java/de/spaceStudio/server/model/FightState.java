package de.spaceStudio.server.model;

public enum FightState {
    WAITING_FOR_TURN("Waiting"), PLAYING("Playing");

    private String state;

    FightState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
