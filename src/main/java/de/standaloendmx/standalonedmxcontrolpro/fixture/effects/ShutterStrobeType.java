package de.standaloendmx.standalonedmxcontrolpro.fixture.effects;

public enum ShutterStrobeType {

    OPEN("Open"), CLOSE("Close"), STROBE("Strobe"), PULSE("Pulse"), RAMP_UP("RampUp"), RAMP_DOWN("RampDown"),
    RAMP_UP_DOWN("RampUpDown"), LIGHTING("Lighting"), SPIKES("Spikes"), BURST("Burst");

    private String name;

    ShutterStrobeType(String name) {
        this.name = name;
    }

    public static ShutterStrobeType getByName(String name) {
        for (ShutterStrobeType value : values()) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
