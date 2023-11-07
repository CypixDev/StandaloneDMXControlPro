package de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyAddScene extends VBox implements Initializable {

    private Logger logger = LogManager.getLogger(MyScene.class);

    @FXML
    private VBox vBoxAdd;


    public MyAddScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/edit/scene/MyAddSceneView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getStyleClass().add("addButton");
    }

    public VBox getVBoxAdd() {
        return vBoxAdd;
    }
}
