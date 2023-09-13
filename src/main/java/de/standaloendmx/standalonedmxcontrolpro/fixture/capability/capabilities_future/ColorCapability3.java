package de.standaloendmx.standalonedmxcontrolpro.fixture.capability.capabilities_future;

import de.standaloendmx.standalonedmxcontrolpro.fixture.capability.CapabilityType;

public class ColorCapability3 extends ChannelCapability3 {

    private String color;

    public ColorCapability3(CapabilityType type, String color) {
        super(type);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ColorCapability{" +
                "color='" + color + '\'' +
                '}';
    }
}
