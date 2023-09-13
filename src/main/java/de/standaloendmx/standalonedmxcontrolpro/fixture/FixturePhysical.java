package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.annotations.JsonAdapter;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.FixturePowerConnector;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.PhysicalDeserializer;

import java.util.List;

@JsonAdapter(PhysicalDeserializer.class)
public class FixturePhysical {

    private FixtureDimension dimension;
    private double weight;
    private int power;
    private String dmxConnector;
    private List<FixturePowerConnector> powerConnectors;
    private FixtureBulb bulb;
    private String lens;


    public FixturePhysical(FixtureDimension dimension, double weight, int power, String dmxConnector, List<FixturePowerConnector> powerConnectors, FixtureBulb bulbType, String lens) {
        this.dimension = dimension;
        this.weight = weight;
        this.power = power;
        this.dmxConnector = dmxConnector;
        this.powerConnectors = powerConnectors;
        this.bulb = bulbType;
        this.lens = lens;
    }

    @Override
    public String toString() {
        return "FixturePhysical{" +
                "dimension=" + dimension +
                ", weight=" + weight +
                ", power=" + power +
                ", dmxConnector='" + dmxConnector + '\'' +
                ", powerConnectors=" + powerConnectors +
                ", bulb=" + bulb +
                ", lens='" + lens + '\'' +
                '}';
    }
}
