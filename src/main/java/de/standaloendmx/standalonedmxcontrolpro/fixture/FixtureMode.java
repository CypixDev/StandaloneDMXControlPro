package de.standaloendmx.standalonedmxcontrolpro.fixture;

import java.util.Arrays;

public class FixtureMode {

    private String name;
    private String shortName;
    private final boolean physicalOverride = false;
    private FixtureChannel[] fixtureChannels;

    @Override
    public String toString() {
        return "FixtureMode{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", physicalOverride=" + physicalOverride +
                ", dmxChannels=" + Arrays.toString(fixtureChannels) +
                '}';
    }
}
