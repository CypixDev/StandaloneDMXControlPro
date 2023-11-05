package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FixtureSelectViewController implements Initializable {

    @FXML
    private FlowPane flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*        flowPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWrapLength = newValue.doubleValue();
            flowPane.setPrefWrapLength(newWrapLength);
        });*/

        for (PatchFixture patch : StandaloneDMXControlPro.instance.getPatchManager().getPatches()) {
            flowPane.getChildren().add(new SelectableFixture(patch));
        }

    }
}
