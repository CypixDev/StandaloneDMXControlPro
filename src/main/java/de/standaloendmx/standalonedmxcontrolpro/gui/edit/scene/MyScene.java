package de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.ScenesViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.group.GroupColor;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties.StepsViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties.TableStep;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MyScene extends VBox implements Initializable {

    private Logger logger = LogManager.getLogger(MyScene.class);

    @FXML
    private VBox container;

    @FXML
    private Label name;

    @FXML
    private Label time;

    @FXML
    private Label repeat;

    private GroupColor color;

    private ObservableList<TableStep> steps;

    public MyScene(GroupColor color) {
        steps = FXCollections.observableArrayList();
        steps.add(new TableStep(0, "00:00:00", "00:01:00", new HashMap<>()));

        this.color = color;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/edit/scene/MySceneView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStyleClass().add(color.getBorderStyleClassName());

        container.setOnMouseClicked(event -> {
            if (container.getStyleClass().contains("scene_selected")) {
                container.getStyleClass().remove("scene_selected");
            } else {
                if (!event.isControlDown()) {
                    ScenesViewController.instance.unselectAllScenes();
                }
                container.getStyleClass().add("scene_selected");
            }
            StepsViewController.instance.update();
            StepsViewController.instance.selectFirstStep();
        });
    }

    public ObservableList<TableStep> getSteps() {
        return steps;
    }
}
