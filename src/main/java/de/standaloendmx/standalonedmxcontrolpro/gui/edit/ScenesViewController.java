package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.MyScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ScenesViewController implements Initializable {


    @FXML
    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hBox.getChildren().add(new MyScene());

    }
}
