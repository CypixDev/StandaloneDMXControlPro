package de.standaloendmx.standalonedmxcontrolpro.patch;

import javafx.scene.paint.Color;

public class PatchFixture {

    //Ein patch auf dem Patch-Grid

    private String name;
    private int channel; // 1 - 512
    private int size;
    private Color color;

    public PatchFixture(String name, int channel, int size, Color color) {
        this.name = name;
        this.channel = channel;
        this.size = size;
        this.color = color;
    }

    public String getName() {
        return name;
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
