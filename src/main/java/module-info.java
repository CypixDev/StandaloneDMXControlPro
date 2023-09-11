module de.standaloendmx.standalonedmxcontrollpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires log4j;

    opens de.standaloendmx.standalonedmxcontrolpro.gui.loading to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.main to javafx.fxml;
    opens de.standaloendmx.standalonedmxcontrolpro.gui.patch to javafx.fxml;

    exports de.standaloendmx.standalonedmxcontrolpro.gui.loading;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.main;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.patch;
}