package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements.ColorWheelPaletteElement;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements.DimmerPaletteElement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PaletteViewController implements Initializable {

    @FXML
    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hBox.getChildren().add(new ColorWheelPaletteElement());
        hBox.getChildren().add(new DimmerPaletteElement());
    }
}
