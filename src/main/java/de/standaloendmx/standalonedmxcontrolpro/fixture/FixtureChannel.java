package de.standaloendmx.standalonedmxcontrolpro.fixture;

import de.standaloendmx.standalonedmxcontrolpro.fixture.capability.ChannelCapability;
import de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability.DMXRange;

import java.util.List;

public class FixtureChannel {

    private String name;
    private int defaultValue;
    private List<ChannelCapability> channelCapabilities;

    public FixtureChannel(String name, int defaultValue, List<ChannelCapability> channelCapabilities) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.channelCapabilities = channelCapabilities;
    }

    public String getName() {
        return name;
    }

    public int getDefaultValue() {
        return defaultValue;
    }


    public List<ChannelCapability> getChannelCapabilities() {
        return channelCapabilities;
    }

    @Override
    public String toString() {
        return "FixtureChannel{" +
                "name='" + name + '\'' +
                ", defaultValue=" + defaultValue +
                ", channelCapabilities=" + channelCapabilities +
                '}';
    }
}
