package de.standaloendmx.standalonedmxcontrolpro.fixture.enums;

public enum FixtureCategories {

    BARREL_SCANNER("Barrel Scanner"), BLINDER("Blinder"), COLOR_CHANGER("Color Changer"), DIMMER("Dimmer"),
    EFFECT("Effect"), FAN("Fan"), FLOWER("Flower"), HAZER("Hazer"), LASER("Laser"), MATRIX("Matrix"),
    MOVING_HEAD("Moving Head"), PIXEL_BAR("Pixel Bar"), SCANNER("Scanner"), SMOKE("Smoke"),
    STAND("Stand"), STROBE("Strobe"), OTHER("Other");

    private String name;

    FixtureCategories(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFileName(){
        return this.toString().toLowerCase().replace("_", "-");
    }

    public static FixtureCategories getByName(String name){
        for (FixtureCategories value : values()) {
            if(value.getName().equals(name)) return value;
        }
        return null;
    }
}
