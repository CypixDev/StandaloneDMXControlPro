package de.standaloendmx.standalonedmxcontrolpro.gui.edit.group;

import javafx.scene.paint.Color;

import java.util.Random;

public enum GroupColor {

    ORANGE(Color.valueOf("#ff9900")), LIME(Color.valueOf("#39ff14")), PURPLE(Color.PURPLE),
    RED(Color.RED), PINK(Color.PINK), BLUE(Color.BLUE), AQUA(Color.AQUA), GREEN(Color.LIGHTGREEN),
    WHITE(Color.WHITE);


    private Color color;

    GroupColor(Color color) {
        this.color = color;
    }

    public static GroupColor getRandom() {
        int r = new Random().nextInt(values().length);
        return values()[r];
    }

    public Color getColor() {
        return color;
    }

    public String getLabelStyleClassName(){
        return "text_"+this.name().toLowerCase();
    }
    public String getBorderStyleClassName(){
        return "broder_"+this.name().toLowerCase();
    }
}
