package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.group.MyGroupBarLabel;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.group.MyGroupContainer;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.MyScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScenesViewController implements Initializable {


    public static ScenesViewController instance;

    @FXML
    private VBox container;

    @FXML
    private HBox bar;

    @FXML
    private HBox groupHBox;

    @FXML
    private Button btnAddGroup;

    private ObservableList<MyGroupContainer> groupList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        groupList = FXCollections.observableArrayList();

        btnAddGroup.setOnAction(e -> {
            addGroupContainer();
        });

        addGroupContainer();


    }

    public void unselectAllScenes() {
        groupList.forEach(MyGroupContainer::setUnSelected);
    }

    public void addGroupContainer() {
        MyGroupBarLabel l = new MyGroupBarLabel();
        bar.getChildren().add(l);
        MyGroupContainer group = new MyGroupContainer(l);
        l.setGroupParent(group);
        groupList.add(group);
        groupHBox.getChildren().add(group);

        l.setOnMouseClicked(event -> {
            resetSelectedGroups();
            ((MyGroupBarLabel) event.getSource()).getGroupParent().setSelected();
            if (event.getClickCount() == 2) {
                l.openNameInputPopup();
            }
        });
    }

    public void removeGroupContainer(MyGroupContainer group) {
        groupList.remove(group);
        groupHBox.getChildren().remove(group);
        bar.getChildren().remove(group.getGroupBarLabel());
    }

    /**
     * Returns all scenes from all groups. But not the add scene(hopefully)
     *
     * @return
     */
    public List<MyScene> getAllScenes() {
        List<MyScene> list = new ArrayList<>();
        for (MyGroupContainer myGroupContainer : groupList) {
            for (Node child : myGroupContainer.vBox.getChildren()) {
                if (child instanceof MyScene scene) {
                    if (!scene.getStyleClass().contains("addButton")) {
                        list.add(scene);
                    }
                }
            }
        }

        return list;
    }

    /**
     * Returns null if more than one scene is selected!
     *
     * @return
     */
    public MyScene getSelectedScene() {
        MyScene ret = null;
        for (MyScene allScene : getAllScenes()) {
            if (allScene.getStyleClass().contains("scene_selected")) {
                if (ret != null) return null;
                ret = allScene;
            }
        }
        return ret;
    }

    private void resetSelectedGroups() {
        groupList.forEach(MyGroupContainer::setUnSelected);
    }
}
