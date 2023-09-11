package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarViewController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private Button btnPatch;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnLive;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnPatch.setOnAction(e -> {
            try {
                ContentAreaViewController.instance.setContentAndAnchor("/gui/patch/PatchDirectoryView.fxml");
                ContentAreaViewController.instance.setContentAndAnchor("/gui/patch/PatchGridView.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


    }
}
