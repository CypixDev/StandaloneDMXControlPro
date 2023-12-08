package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements.ColorWheelPaletteElement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PaletteViewController implements Initializable {

    @FXML
    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox.getChildren().add(new ColorWheelPaletteElement());
    }
}
