package de.spaceStudio.server.model;

public class PairInt {
    private final int key;
    private final int value;

    public PairInt(int aKey, int aValue) {


        key = aKey;
        value = aValue;
    }

    public int key() {
        return key;
    }

    public int value() {
        return value;
    }
}

