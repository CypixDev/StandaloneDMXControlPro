package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.annotations.JsonAdapter;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.CategoriesDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.ChannelDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.LinkDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.fixture.enums.FixtureCategories;
import de.standaloendmx.standalonedmxcontrolpro.fixture.enums.FixtureLinkType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Fixture {

    public String manufacturerKey;
    private String name;
    private String fixtureKey;
    private String shortName;
    @JsonAdapter(CategoriesDeserializer.class)
    private List<FixtureCategories> categories;
    private String comment;
    private String RMD;
    @JsonAdapter(LinkDeserializer.class)
    private Map<FixtureLinkType, List<String>> links;
    private FixturePhysical physical;

    @JsonAdapter(ChannelDeserializer.class)
    private List<FixtureChannel> availableChannels;
    private FixtureMode[] modes;



    @Override
    public String toString() {
        return "Fixture{" +
                "manufacture='" + manufacturerKey+ '\'' +
                "\n, name='" + name + '\'' +
                "\n, shortName='" + shortName + '\'' +
                "\n, category=" + Arrays.asList(categories).toString() +
                "\n, comment='" + comment + '\'' +
                "\n, RMD='" + RMD + '\'' +
                "\n, links='" + links.toString() + '\'' +
                "\n, physical=" + physical.toString() +
                "\n, modes=" + Arrays.toString(modes) +
                '}';
    }
}
