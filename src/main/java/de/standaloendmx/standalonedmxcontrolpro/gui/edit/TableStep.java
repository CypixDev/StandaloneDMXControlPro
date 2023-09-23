package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import java.time.LocalTime;

public class TableStep {

    private int pos;
    private int fadeTime;
    private int waitTime;

    public TableStep(int pos, int fadeTime, int waitTime) {
        this.pos = pos;
        this.fadeTime = fadeTime;
        this.waitTime = waitTime;
    }

    public int getPos() {
        return pos;
    }

    public int getFadeTime() {
        return fadeTime;
    }

    public int getWaitTime() {
        return waitTime;
    }
}
