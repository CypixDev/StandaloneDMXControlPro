package de.standaloendmx.standalonedmxcontrolpro.fixture;

import java.util.Arrays;
import java.util.List;

public class FixtureMode {

    private String name;
    private String shortName;
    private final boolean physicalOverride = false;
    private List<String> fixtureChannels;

    public FixtureMode(String name, String shortName, List<String> fixtureChannels) {
        this.name = name;
        this.shortName = shortName;
        this.fixtureChannels = fixtureChannels;
    }

    public String getName() {
        return name;
    }
    public String getNameWithChannel(){
        if(!name.contains("ch"))
            return name+" ("+fixtureChannels.size()+"CH)";
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean isPhysicalOverride() {
        return physicalOverride;
    }

    public List<String> getFixtureChannels() {
        return fixtureChannels;
    }

    @Override
    public String toString() {
        return "FixtureMode{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", physicalOverride=" + physicalOverride +
                ", fixtureChannels=" + fixtureChannels +
                '}';
    }
}
