module de.standaloendmx.standalonedmxcontrollpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires log4j;
    requires java.desktop;
    requires com.google.gson;
    requires com.fazecast.jSerialComm;
    requires it.unimi.dsi.fastutil.core;

    //GUI

    opens de.standaloendmx.standalonedmxcontrolpro.gui.loading to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.main to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.patch to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.edit to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.edit.group to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.bottombar to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.deploy to javafx.fxml, com.google.gson;

    exports de.standaloendmx.standalonedmxcontrolpro.gui.loading;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.main;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.patch;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.edit;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.edit.group;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.bottombar;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.deploy;
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
    opens de.standaloendmx.standalonedmxcontrolpro.patch to com.google.gson;
    exports de.standaloendmx.standalonedmxcontrolpro.patch to com.google.gson;

    opens de.standaloendmx.standalonedmxcontrolpro.manager to com.google.gson;


}