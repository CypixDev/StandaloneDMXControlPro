package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarViewController implements Initializable {

    private final Logger logger = LogManager.getLogger(SideBarViewController.class);

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

    @FXML
    private Button btnDeploy;

    @FXML
    private FontIcon deployIcon;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastClickedButton = btnEdit;
        handleButtonClick(btnPatch);

        btnPatch.setOnAction(e -> handleButtonClick(btnPatch));
        btnEdit.setOnAction(e -> handleButtonClick(btnEdit));
        btnLive.setOnAction(e -> handleButtonClick(btnLive));
        btnDeploy.setOnAction(e -> handleButtonClick(btnDeploy));

    }

    /**
     * Handles the click event of a button.
     * Resets the content area view, updates the style of the clicked button and sets the content and anchor based on the clicked button.
     *
     * @param clickedButton The button that was clicked.
     */
    public void handleButtonClick(Button clickedButton) {
        if (!lastClickedButton.equals(clickedButton)) {
            ContentAreaViewController.instance.resetContent();


            lastClickedButton.getGraphic().getStyleClass().remove("selected");
            lastClickedButton.getGraphic().getStyleClass().add("not-selected");
            clickedButton.getGraphic().getStyleClass().remove("not-selected");
            clickedButton.getGraphic().getStyleClass().add("selected");


            if (clickedButton.equals(btnPatch)) {
                try {
                    ContentAreaViewController.instance.setContentAndAnchor("/gui/patch/PatchDirectoryView.fxml");
                    ContentAreaViewController.instance.setContentAndAnchor("/gui/patch/PatchGridView.fxml");
                } catch (IOException ex) {
                    logger.error(ex);
                }
            } else if (clickedButton.equals(btnEdit)) {
                try {
                    ContentAreaViewController.instance.setContentAndAnchor(Views.EDIT_VIEW, Priority.ALWAYS);
                } catch (IOException ex) {
                    logger.error(ex);
                }
            } else if (clickedButton.equals(btnLive)) {

            } else if (clickedButton.equals(btnDeploy)) {
                try {
                    ContentAreaViewController.instance.setContentAndAnchor("/gui/deploy/DeployView.fxml", Priority.ALWAYS);
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }

            lastClickedButton = clickedButton;
        }
    }
}
