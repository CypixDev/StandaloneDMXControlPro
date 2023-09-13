package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.annotations.JsonAdapter;


public class FixtureDimension {
    private double width;
    private double height;
    private double depth;

    public FixtureDimension(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "FixtureDimension{" +
                "width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                '}';
    }
}