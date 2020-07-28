package de.spaceStudio.server.model;

public enum LobbyState {
    WAITING("Waiting"), READY("Ready");


    private String state;

    LobbyState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
