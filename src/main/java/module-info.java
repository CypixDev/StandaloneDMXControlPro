module de.standaloendmx.standalonedmxcontrollpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;


    opens de.standaloendmx.standalonedmxcontrolpro.gui.loading to javafx.fxml;
    exports de.standaloendmx.standalonedmxcontrolpro.gui.loading;
    exports de.standaloendmx.standalonedmxcontrolpro.main;
    opens de.standaloendmx.standalonedmxcontrolpro.main to javafx.fxml;
}