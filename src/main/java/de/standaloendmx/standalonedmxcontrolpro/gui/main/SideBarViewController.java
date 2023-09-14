package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarViewController implements Initializable {


    private Button lastClickedButton;

    @FXML
    private VBox vBox;

    @FXML
    private Button btnPatch;

    @FXML
    private FontIcon patchIcon;

    @FXML
    private Button btnEdit;

    @FXML
    private FontIcon editIcon;

    @FXML
    private Button btnLive;

    @FXML
    private FontIcon liveIcon;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastClickedButton = btnPatch;
        handleButtonClick(btnPatch);

        btnPatch.setOnAction(e -> {
            handleButtonClick(btnPatch);
        });
        btnEdit.setOnAction(e -> {
            handleButtonClick(btnEdit);
        });
        btnLive.setOnAction(e -> {
            handleButtonClick(btnLive);
        });

    }
    public void handleButtonClick(Button clickedButton){
        if(!lastClickedButton.equals(clickedButton)){
            ContentAreaViewController.instance.resetContent();


            ((FontIcon)lastClickedButton.getGraphic()).getStyleClass().remove("selected");
            ((FontIcon)lastClickedButton.getGraphic()).getStyleClass().add("not-selected");
            ((FontIcon)clickedButton.getGraphic()).getStyleClass().remove("not-selected");
            ((FontIcon)clickedButton.getGraphic()).getStyleClass().add("selected");


            if (clickedButton.equals(btnPatch)) {
                try {
                    ContentAreaViewController.instance.setContentAndAnchor("/gui/patch/PatchDirectoryView.fxml");
                    ContentAreaViewController.instance.setContentAndAnchor("/gui/patch/PatchGridView.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (clickedButton.equals(btnEdit)) {

            } else if (clickedButton.equals(btnLive)) {

            }

            lastClickedButton = clickedButton;
        }
    }
}
