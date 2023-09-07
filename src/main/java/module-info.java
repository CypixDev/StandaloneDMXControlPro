module de.standaloendmx.standalonedmxcontrollpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens de.standaloendmx.standalonedmxcontrollpro to javafx.fxml;
    exports de.standaloendmx.standalonedmxcontrollpro;
}