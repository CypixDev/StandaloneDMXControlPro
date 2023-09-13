package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

public class FixturePowerConnector {
    private String name;
    private String comment;

    public FixturePowerConnector(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "FixturePowerConnector{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
