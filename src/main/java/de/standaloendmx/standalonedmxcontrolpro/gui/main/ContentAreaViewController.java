package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContentAreaViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ContentAreaViewController.class);

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

        splitPane.getItems().add(StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.BOTTOM_BAR));


/*        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(5),
                actionEvent -> splitPane.setDividerPosition(0, 0.7)
        ));

        timeline.play();*/
    }

    /**
     * Adds the specified {@link Node} to the contentBox and sets the horizontal grow behavior using the given {@link Priority}.
     *
     * @param node  the Node to add to the contentBox
     * @param hGrow the horizontal grow behavior for the Node
     */
    public void setContentAndAnchor(Node node, Priority hGrow) {
        contentBox.getChildren().add(node);
        HBox.setHgrow(node, hGrow);
    }

    /**
     * Adds the specified Node to the contentBox and sets the horizontal grow behavior using the given Priority.
     *
     * @param node  the Node to add to the contentBox
     * @param hGrow the horizontal grow behavior for the Node
     * @throws IOException if there is an error loading the Node from the specified path
     */
    public void setContentAndAnchor(String path) throws IOException {
        setContentAndAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))), Priority.NEVER);
    }

    /**
     * Sets the content and anchor of the node using the given path and horizontal grow priority.
     * If the content cannot be loaded from the path, an IOException is thrown.
     *
     * @param path  the path to load the content from
     * @param hGrow the priority to apply when resizing the node horizontally
     * @throws IOException if the content cannot be loaded from the path
     */
    public void setContentAndAnchor(String path, Priority hGrow) throws IOException {
        try {
            setContentAndAnchor((Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path))), hGrow);
        } catch (LoadException e) {
            logger.error(e);
        }

    }
    public void setContentAndAnchor(Views view, Priority hGrow) throws IOException {
        try {
            setContentAndAnchor(StandaloneDMXControlPro.instance.getViewManager().getLoadedView(view), hGrow);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    /**
     * Resets the content of the contentBox by removing all children nodes.
     */
    public void resetContent() {
        contentBox.getChildren().clear();
    }
}
