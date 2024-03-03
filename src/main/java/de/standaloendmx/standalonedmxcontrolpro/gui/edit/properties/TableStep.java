package de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties;

import java.util.Map;

public class TableStep {

    private int pos;
    private String fadeTime;
    private String holdTime;

    private final Map<Integer, Byte> channelValues;

    public TableStep(int pos, String fadeTime, String holdTime, Map<Integer, Byte> channelValues) {
        this.pos = pos;
        this.fadeTime = fadeTime;
        this.holdTime = holdTime;
        this.channelValues = channelValues;
    }

    public Map<Integer, Byte> getChannelValues() {
        return channelValues;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getFadeTime() {
        return fadeTime;
    }

    public void setFadeTime(String fadeTime) {
        this.fadeTime = fadeTime;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(String holdTime) {
        this.holdTime = holdTime;
    }
}
