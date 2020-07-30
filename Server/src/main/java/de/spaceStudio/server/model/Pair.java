package de.spaceStudio.server.model;

public class Pair {
    private final Float left;
    private final Float right;

    public Pair(Float aKey, Float aValue) {


        left = aKey;
        right = aValue;
    }

    public Float getLeft() {
        return left;
    }

    public Float getRight() {
        return right;
    }
}

