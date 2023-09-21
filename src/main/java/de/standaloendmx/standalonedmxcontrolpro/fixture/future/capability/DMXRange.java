package de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability;

public class DMXRange {

    private int from;
    private int to;

    public DMXRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isInRange(int dmxValue) {
        return dmxValue >= from && dmxValue <= to;
    }

    @Override
    public String toString() {
        return "DMXRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
