package de.standaloendmx.standalonedmxcontrolpro.patch;

import de.standaloendmx.standalonedmxcontrolpro.fixture.Fixture;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureMode;
import javafx.scene.paint.Color;

public class PatchFixture {

    //Ein patch auf dem Patch-Grid

    private Fixture fixture;
    private int channel; // 1 - 512
    private int size;
    private Color color;

    public PatchFixture(Fixture fixture, int channel, int size, Color color) {
        this.channel = channel;
        this.size = size;
        this.color = color;
        this.fixture = fixture;
    }


    public Fixture getFixture() {
        return fixture;
    }

    public String getName(){
        return fixture.getName();
    }

    public int getChannel() {
        return channel;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}
