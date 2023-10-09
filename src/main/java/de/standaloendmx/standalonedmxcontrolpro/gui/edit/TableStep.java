package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import java.time.LocalTime;

public class TableStep {

    private int pos;
    private String fadeTime;
    private int waitTime;

    public TableStep(int pos, String fadeTime, int waitTime) {
        this.pos = pos;
        this.fadeTime = fadeTime;
        this.waitTime = waitTime;
    }

    public int getPos() {
        return pos;
    }

    public String getFadeTime() {
        return fadeTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setFadeTime(String fadeTime) {
        this.fadeTime = fadeTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}
