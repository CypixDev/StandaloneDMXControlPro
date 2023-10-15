package de.standaloendmx.standalonedmxcontrolpro.gui.edit.group;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.MyAddScene;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.MyScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyGroupContainer extends VBox implements Initializable {

    private Logger logger = LogManager.getLogger(MyGroupContainer.class);

    @FXML
    private VBox vBox;

    private GroupColor color;

    private final MyGroupBarLabel groupBarLabel;


    public MyGroupContainer(MyGroupBarLabel l) {
        color = GroupColor.getRandom();
        this.groupBarLabel = l;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/edit/group/MyGroupContainer.fxml"));
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
        MyScene scene = new MyScene(color);
        vBox.getChildren().add(scene);

        MyAddScene addButton = new MyAddScene();
        vBox.getChildren().add(addButton);

        addButton.setOnMouseClicked(event -> {
            addNodeBeforeLast(new MyScene(color));
        });
    }

    private void addNodeBeforeLast(Node node) {
        int lastIndex = vBox.getChildren().size() - 1;
        if (lastIndex >= 0) {
            vBox.getChildren().add(lastIndex, node);
        }
    }

    public void setSelected(){
        vBox.getChildren().forEach(child -> {
            child.getStyleClass().add("scene_selected");
        });

        groupBarLabel.getStyleClass().add(color.getLabelStyleClassName());
        groupBarLabel.getStyleClass().add("label_group_selected");
        //groupBarLabel.setTextFill(color.getColor());

    }

    public void setUnSelected(){
        vBox.getChildren().forEach(child -> {
            child.getStyleClass().remove("scene_selected");
        });

        //TODO remove color too!
        //groupBarLabel.setTextFill(Color.valueOf("#7a7a7a"));
        groupBarLabel.getStyleClass().remove("label_group_selected");
        groupBarLabel.getStyleClass().remove(color.getLabelStyleClassName());
    }

    public MyGroupBarLabel getGroupBarLabel() {
        return groupBarLabel;
    }
}
