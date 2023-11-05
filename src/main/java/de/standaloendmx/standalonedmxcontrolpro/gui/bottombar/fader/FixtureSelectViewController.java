package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.ScenesViewController;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import javafx.collections.FXCollections;
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


    private ObservableList<SelectableFixture> fixtures;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fixtures = FXCollections.observableArrayList();


        for (PatchFixture patch : StandaloneDMXControlPro.instance.getPatchManager().getPatches()) {
            SelectableFixture fx = new SelectableFixture(patch);
            flowPane.getChildren().add(fx);
            fixtures.add(fx);
        }

        for (SelectableFixture fixture : fixtures) {
            fixture.getStyleClass().add("not_selected");
            fixture.setOnMouseClicked(event -> {
                if(fixture.getStyleClass().contains("selected")){
                    fixture.getStyleClass().remove("selected");
                    fixture.getStyleClass().add("not_selected");
                }else {
                    fixture.getStyleClass().remove("not_selected");
                    fixture.getStyleClass().add("selected");
                }
            });
        }
    }
}
