package de.standaloendmx.standalonedmxcontrolpro.patch;

public class PatchFixture {

    //Ein patch auf dem Patch-Grid

    private de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture patchFixture;
    private int channel; // 1 - 512
    private int size;
    private String color;

    public PatchFixture(de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture patchFixture, int channel, int size, String color) {
        this.channel = channel;
        this.size = size;
        this.color = color;
        this.patchFixture = patchFixture;
    }


    public de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture getFixture() {
        return patchFixture;
    }

    public String getName() {
        return patchFixture.getName();
    }

    public int getChannel() {
        return channel;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
