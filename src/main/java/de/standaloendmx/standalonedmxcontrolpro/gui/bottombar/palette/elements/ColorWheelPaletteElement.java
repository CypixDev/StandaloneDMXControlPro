package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.ColorWheel;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorWheelPaletteElement extends PaletteElementViewController {

    public ColorWheelPaletteElement() {
        super();
        content.getChildren().add(new ColorWheel());
        label.setText("RGB");
    }


    //Not called actually why so ever ?!?!
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
