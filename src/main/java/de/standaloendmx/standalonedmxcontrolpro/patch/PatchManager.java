package de.standaloendmx.standalonedmxcontrolpro.patch;

import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureManager;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PatchManager {

    private List<PatchFixture> patches;

    public PatchManager() {
        patches = new ArrayList<>();
        //patches.add(new PatchFixture(StandaloneDMXControlPro.instance.getFixtureManager().getFixtures().get(0), 4, 3, Color.AQUAMARINE));
    }

    public boolean isChannelFree(int pos, int size){
        for (PatchFixture patch : patches) {
            if(pos+size > patch.getChannel() && patch.getChannel()+patch.getSize() > pos)
                return false;
        }
        return true;
    }

    public PatchFixture getPatchByChannel(int channel){
        for (PatchFixture patch : patches) {
            if(patch.getChannel() == channel) return patch;
        }
        return null;
    }

    public List<PatchFixture> getPatches() {
        return patches;
    }
}
