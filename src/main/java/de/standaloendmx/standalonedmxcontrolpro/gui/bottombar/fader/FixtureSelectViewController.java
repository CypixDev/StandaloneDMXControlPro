package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FixtureSelectViewController implements Initializable {

    @FXML
    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (PatchFixture patch : StandaloneDMXControlPro.instance.getPatchManager().getPatches()) {
            hBox.getChildren().add(new SelectableFixture(patch));
        }

    }
}
