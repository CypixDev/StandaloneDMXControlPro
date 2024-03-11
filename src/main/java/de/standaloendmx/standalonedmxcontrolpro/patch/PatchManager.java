package de.standaloendmx.standalonedmxcontrolpro.patch;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.FaderViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fixtureselect.FixtureSelectViewController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class PatchManager {

    private final ObservableList<PatchFixture> patches;

    public PatchManager() {
        patches = FXCollections.observableArrayList();
        //patches.add(new PatchFixture(StandaloneDMXControlPro.instance.getFixtureManager().getFixtures().get(0), 4, 3, Color.AQUAMARINE));
        patches.addListener(new ListChangeListener<PatchFixture>() {
            @Override
            public void onChanged(Change<? extends PatchFixture> c) {
                //TODO implement
            }
        });
    }

    public boolean isChannelFree(int pos, int size) {
        for (PatchFixture patch : patches) {
            if (pos + size > patch.getChannel() && patch.getChannel() + patch.getSize() > pos)
                return false;
        }
        return true;
    }

    public PatchFixture getPatchByChannel(int channel) {
        for (PatchFixture patch : patches) {
            if (patch.getChannel() == channel) return patch;
        }
        return null;
    }

    public void addPatch(PatchFixture patchFixture) {
        patches.add(patchFixture);
        FixtureSelectViewController.instance.addToSelectable(patchFixture);

        FaderViewController.instance.updateSliders();
    }

    public List<PatchFixture> getPatches() {
        return patches;
    }

    public void removePatch(int channelByPane) {
        PatchFixture patch = getPatchByChannel(channelByPane);
        FixtureSelectViewController.instance.removeToSelectable(patch);
        patches.remove(patch);

        FaderViewController.instance.updateSliders();
    }
}
