package de.standaloendmx.standalonedmxcontrolpro.fixture;

public class FixtureBulb {

    private String type;
    private int colorTemperature;
    private int lumens;

    public FixtureBulb(String type, int colorTemperature, int lumens) {
        this.type = type;
        this.colorTemperature = colorTemperature;
        this.lumens = lumens;
    }

    public String getType() {
        return type;
    }

    public int getColorTemperature() {
        return colorTemperature;
    }

    public int getLumens() {
        return lumens;
    }

    @Override
    public String toString() {
        return "FixtureBulb{" +
                "type='" + type + '\'' +
                ", colorTemperature=" + colorTemperature +
                ", lumens=" + lumens +
                '}';
    }
}
