package de.standaloendmx.standalonedmxcontrolpro.fixture.capability;

import de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability.DMXRange;

public class ChannelCapability {

    private CapabilityType type;
    private String effectName;
    private DMXRange dmxRange;

    public ChannelCapability(CapabilityType type, String effectName, DMXRange dmxRange) {
        this.type = type;
        this.effectName = effectName;
        this.dmxRange = dmxRange;
    }

    public CapabilityType getType() {
        return type;
    }

    public String getEffectName() {
        return effectName;
    }

    public DMXRange getDmxRange() {
        return dmxRange;
    }

    @Override
    public String toString() {
        return "ChannelCapability{" +
                "type=" + type +
                ", effectName='" + effectName + '\'' +
                ", dmxRange=" + dmxRange +
                '}';
    }
}
