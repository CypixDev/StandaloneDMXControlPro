package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaletteElementViewController extends VBox{

    private final Logger logger = LogManager.getLogger(PaletteElementViewController.class);

    @FXML
    protected VBox vBox;

    @FXML
    protected Label label;

    @FXML
    protected FontIcon icon;

    @FXML
    protected Pane content;

    public PaletteElementViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/palette/elements/PaletteElementView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }


    public void initialize(URL location, ResourceBundle resources) {}


}
