package de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties;

public class TableStep {

    private int pos;
    private String fadeTime;
    private String holdTime;

    public TableStep(int pos, String fadeTime, String holdTime) {
        this.pos = pos;
        this.fadeTime = fadeTime;
        this.holdTime = holdTime;
    }

    public int getPos() {
        return pos;
    }

    public String getFadeTime() {
        return fadeTime;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setFadeTime(String fadeTime) {
        this.fadeTime = fadeTime;
    }

    public void setHoldTime(String holdTime) {
        this.holdTime = holdTime;
    }
}
