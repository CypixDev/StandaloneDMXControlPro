package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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
    }

    public void setContentAndAnchor(Node node){
        contentBox.getChildren().add(node);
        /*
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);*/
    }
    public void setContentAndAnchor(String path) throws IOException {
        setContentAndAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))));
    }

    public void resetContent() {
        contentBox.getChildren().clear();
    }
}
