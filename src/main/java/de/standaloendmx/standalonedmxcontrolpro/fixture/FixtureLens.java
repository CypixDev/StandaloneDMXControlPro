package de.standaloendmx.standalonedmxcontrolpro.fixture;

public class FixtureLens {

    private String name;
    private String degreeMinMax;

    public FixtureLens(String name, String degreeMinMax) {
        this.name = name;
        this.degreeMinMax = degreeMinMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegreeMinMax() {
        return degreeMinMax;
    }

    public void setDegreeMinMax(String degreeMinMax) {
        this.degreeMinMax = degreeMinMax;
    }

    @Override
    public String toString() {
        return "FixtureLens{" +
                "name='" + name + '\'' +
                ", degreeMinMax='" + degreeMinMax + '\'' +
                '}';
    }
}
