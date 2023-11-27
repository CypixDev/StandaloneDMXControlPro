package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BottomBarViewController implements Initializable {

    private final Logger logger = LogManager.getLogger(BottomBarViewController.class);

    private Button lastClickedButton;

    @FXML
    private AnchorPane content;


    @FXML
    private Button btnSlider;
    @FXML
    private Button btnPallet;
    @FXML
    private Button btnEffect;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastClickedButton = btnEffect;
        handleButtonClick(btnSlider);

        btnSlider.setOnAction(e -> {
            handleButtonClick(btnSlider);
        });
        btnPallet.setOnAction(e -> {
            handleButtonClick(btnPallet);
        });
        btnEffect.setOnAction(e -> {
            handleButtonClick(btnEffect);
        });

    }

    public void handleButtonClick(Button clickedButton) {
        if (!lastClickedButton.equals(clickedButton)) {
            resetContent();


            ((FontIcon) lastClickedButton.getGraphic()).getStyleClass().remove("selected");
            ((FontIcon) lastClickedButton.getGraphic()).getStyleClass().add("not-selected");
            ((FontIcon) clickedButton.getGraphic()).getStyleClass().remove("not-selected");
            ((FontIcon) clickedButton.getGraphic()).getStyleClass().add("selected");


            if (clickedButton.equals(btnSlider)) {
                try {
                    setContentAndAllAnchor("/gui/bottombar/fader/FaderView.fxml");
                } catch (IOException ex) {
                    logger.error(ex);
                }
            } else if (clickedButton.equals(btnPallet)) {
            } else if (clickedButton.equals(btnEffect)) {

            }

            lastClickedButton = clickedButton;
        }
    }

    public void setContentAndBottomTopAnchor(Node node) {
        content.getChildren().add(node);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
    }

    public void setContentAndAllAnchor(Node node) {
        content.getChildren().add(node);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
    }

    public void setContentAndBottomTopAnchor(String path) throws IOException {
        setContentAndBottomTopAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))));
    }

    public void setContentAndAllAnchor(String path) throws IOException {
        setContentAndAllAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))));
    }

    public void resetContent() {
        content.getChildren().clear();
    }
}
