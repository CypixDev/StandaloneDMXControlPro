package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

public class PatchFixture {
    private String name;

    public PatchFixture(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
