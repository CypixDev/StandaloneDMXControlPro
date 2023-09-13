package de.standaloendmx.standalonedmxcontrolpro.fixture.enums;

public enum FixtureLinkType {

    VIDEO("video"), MANUAL("manual"), PRODUCT_PAGE("productPage"), OTHER("other");

    private String name;

    FixtureLinkType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static FixtureLinkType getByName(String name){
        for (FixtureLinkType value : values()) {
            if(value.getName().equals(name)) return value;
        }
        return null;
    }


}
