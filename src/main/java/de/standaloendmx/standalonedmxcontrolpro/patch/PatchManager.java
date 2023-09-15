package de.standaloendmx.standalonedmxcontrolpro.patch;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PatchManager {

    private List<PatchFixture> patches;

    public PatchManager() {
        patches = new ArrayList<>();
        patches.add(new PatchFixture("Your mama!", 4, 3, Color.AQUAMARINE));
    }


    public List<PatchFixture> getPatches() {
        return patches;
    }
}
