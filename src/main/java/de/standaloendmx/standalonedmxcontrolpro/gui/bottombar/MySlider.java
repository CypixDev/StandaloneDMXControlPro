package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class MySlider extends VBox {

    @FXML
    private Pane color;
    @FXML
    private Label value;

    @FXML
    private Label channel;

    @FXML
    private Slider slider;

    @FXML
    private Button button;

    public MySlider() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/SliderView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        // Hier kannst du die Initialisierung deiner Controls durchführen
        value.textProperty().bind(Bindings.format("%.2f", slider.valueProperty()));
        // Füge Ereignisse oder Aktionen hinzu, wenn der Button oder Slider geklickt wird, etc.
    }
}