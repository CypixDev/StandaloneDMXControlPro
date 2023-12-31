package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContentAreaViewController implements Initializable {

    public static ContentAreaViewController instance;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane content;

    @FXML
    private HBox contentBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(5),
                actionEvent -> {
                    splitPane.setDividerPosition(0, 0.7);
                }
        ));

        timeline.play();
    }

    public void setContentAndAnchor(Node node, Priority hGrow) {
        contentBox.getChildren().add(node);
        HBox.setHgrow(node, hGrow);
        /*
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);*/
    }

    public void setContentAndAnchor(String path) throws IOException {
        setContentAndAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))), Priority.NEVER);
    }

    public void setContentAndAnchor(String path, Priority hGrow) throws IOException {
        try {
            setContentAndAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))), hGrow);
        } catch (LoadException e) {
            e.printStackTrace();
        }

    }

    public void resetContent() {
        contentBox.getChildren().clear();
    }
}
