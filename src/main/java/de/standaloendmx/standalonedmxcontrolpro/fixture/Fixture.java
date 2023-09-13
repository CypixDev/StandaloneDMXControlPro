package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.annotations.JsonAdapter;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.CategoriesDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.ChannelDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.LinkDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.ModeDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.enums.FixtureCategories;
import de.standaloendmx.standalonedmxcontrolpro.fixture.enums.FixtureLinkType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fixture {
    private boolean isMode = false;

    private String manufacture;
    private String name;
    private String fixtureKey;
    private String shortName;
    @JsonAdapter(CategoriesDeserializer.class)
    private List<FixtureCategories> categories;
    private String comment;
    private String RMD;
    @JsonAdapter(LinkDeserializer.class)
    private Map<FixtureLinkType, List<String>> links = new HashMap<>();
    private FixturePhysical physical = new FixturePhysical();

    @JsonAdapter(ChannelDeserializer.class)
    private List<FixtureChannel> availableChannels;
    @JsonAdapter(ModeDeserializer.class)
    private List<FixtureMode> modes;

    public Fixture(String name) { //For tree root item
        this.name = name;
        isMode = true;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getName() {
        return name;
    }

    public String getFixtureKey() {
        return fixtureKey;
    }

    public String getShortName() {
        return shortName;
    }

    public List<FixtureCategories> getCategories() {
        return categories;
    }

    public String getComment() {
        return comment;
    }

    public String getRMD() {
        return RMD;
    }

    public Map<FixtureLinkType, List<String>> getLinks() {
        return links;
    }

    public FixturePhysical getPhysical() {
        return physical;
    }

    public List<FixtureChannel> getAvailableChannels() {
        return availableChannels;
    }

    public List<FixtureMode> getModes() {
        return modes;
    }

    public FixtureMode getModeByName(String name){
        for (FixtureMode mode : getModes()) {
            if(mode.getNameWithChannel().equals(name))return mode;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Fixture{" +
                "manufacture='" + manufacture + '\'' +
                ", name='" + name + '\'' +
                ", fixtureKey='" + fixtureKey + '\'' +
                ", shortName='" + shortName + '\'' +
                ", categories=" + categories +
                ", comment='" + comment + '\'' +
                ", RMD='" + RMD + '\'' +
                ", links=" + links +
                ", physical=" + physical +
                ", availableChannels=" + availableChannels +
                ", modes=" + modes +
                '}';
    }

    public boolean isMode() {
        return isMode;
    }
}
