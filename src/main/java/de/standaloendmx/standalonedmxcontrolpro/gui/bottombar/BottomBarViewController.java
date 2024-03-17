package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
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
    private SplitPane splitPane;


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

        new Timeline(new KeyFrame(
                Duration.seconds(5),
                actionEvent -> {
                    splitPane.setDividerPosition(0, 0.6);
                }
        )).play();

    }

    public void handleButtonClick(Button clickedButton) {
        if (!lastClickedButton.equals(clickedButton)) {
            resetContent();


            lastClickedButton.getGraphic().getStyleClass().remove("selected");
            lastClickedButton.getGraphic().getStyleClass().add("not-selected");
            clickedButton.getGraphic().getStyleClass().remove("not-selected");
            clickedButton.getGraphic().getStyleClass().add("selected");


            if (clickedButton.equals(btnSlider)) {
                try {
                    setContentAndAllAnchor(Views.Fader_VIEW);
                    //setContentAndAllAnchor("/gui/bottombar/fader/FaderView.fxml");
                } catch (IOException ex) {
                    logger.error(ex);
                }
            } else if (clickedButton.equals(btnPallet)) {
                try {
                    setContentAndAllAnchor("/gui/bottombar/palette/PaletteView.fxml");
                } catch (Exception ex) {
                    logger.error(ex);
                }
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

    public void setContentAndAllAnchor(Views view) throws IOException {
        setContentAndAllAnchor(StandaloneDMXControlPro.instance.getViewManager().getLoadedView(view));
    }

    public void resetContent() {
        content.getChildren().clear();
    }
}
