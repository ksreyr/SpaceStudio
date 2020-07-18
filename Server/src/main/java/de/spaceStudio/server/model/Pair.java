package de.spaceStudio.server.model;

public class Pair
{
    private final Float key;
    private final Float value;

    public Pair(Float aKey, Float aValue)
    {


        key   = aKey;
        value = aValue;
    }

    public Float key()   { return key; }
    public Float value() { return value; }
}

