package de.spaceStudio.server.handler;

import java.io.Serializable;

public class SinglePlayerGame implements Serializable {

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    private String difficult;


}
