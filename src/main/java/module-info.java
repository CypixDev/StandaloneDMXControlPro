module de.standaloendmx.standalonedmxcontrollpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires log4j;
    requires java.desktop;
    requires com.google.gson;

    //GUI

    opens de.standaloendmx.standalonedmxcontrolpro.gui.loading to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.main to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.patch to javafx.fxml;

    exports de.standaloendmx.standalonedmxcontrolpro.gui.loading;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.main;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.patch;
    //GUI END

    opens de.standaloendmx.standalonedmxcontrolpro.fixture to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.fixture to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.fixture.enums to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer to com.google.gson;
    opens de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability to com.google.gson;
    opens de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.fixture.capability to com.google.gson;
    opens de.standaloendmx.standalonedmxcontrolpro.fixture.capability to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.fixture.capability.capabilities_future to com.google.gson;
    opens de.standaloendmx.standalonedmxcontrolpro.fixture.capability.capabilities_future to com.google.gson;

}