package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.MyFader;

public class DimmerPaletteElement extends PaletteElementViewController {

    public DimmerPaletteElement() {

        content.getChildren().add(new MyFader(2));

        label.setText("Dimmer");
    }
}
