package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fixtureselect;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.FaderViewController;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class FixtureSelectViewController implements Initializable {

    public static FixtureSelectViewController instance;

    @FXML
    private FlowPane flowPane;

    private ObservableList<SelectableFixture> fixtures;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        fixtures = FXCollections.observableArrayList();

        for (PatchFixture patch : StandaloneDMXControlPro.instance.getPatchManager().getPatches()) {
            addToSelectable(patch);
        }

    }

    public void addToSelectable(PatchFixture patch) {
        SelectableFixture fx = new SelectableFixture(patch);
        flowPane.getChildren().add(fx);
        fixtures.add(fx);

        fx.getStyleClass().add("not_selected");
        fx.setOnMouseClicked(event -> {
            if (fx.getStyleClass().contains("selected")) {
                fx.getStyleClass().remove("selected");
                fx.getStyleClass().add("not_selected");
            } else {
                fx.getStyleClass().remove("not_selected");
                fx.getStyleClass().add("selected");
            }

            FaderViewController.instance.updateSliders();
        });
    }

    public void removeToSelectable(PatchFixture patch) {
        for (SelectableFixture fixture : fixtures) {
            if (fixture.patchFixture.equals(patch)) {
                flowPane.getChildren().remove(fixture);
                fixtures.remove(fixture);
                break;
            }
        }
    }

    public ObservableList<SelectableFixture> getFixtures() {
        return fixtures;
    }
}
