package de.spaceStudio.server.handler;

import java.io.Serializable;

public class MultiPlayerGame implements Serializable {

    public String universe;

    public String getUniverse() {
        return universe;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }
}
