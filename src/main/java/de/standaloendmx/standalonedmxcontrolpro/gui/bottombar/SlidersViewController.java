package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SlidersViewController implements Initializable {

    private List<MySlider> mySliders;

    @FXML
    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mySliders = new ArrayList<>();

        for (int i = 0; i < 512; i++) {
            hBox.getChildren().add(new MySlider());
        }

    }
}
