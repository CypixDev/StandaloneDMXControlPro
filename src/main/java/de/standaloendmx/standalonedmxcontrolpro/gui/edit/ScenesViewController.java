package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.MyScene;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.SceneTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ScenesViewController implements Initializable {

    @FXML
    private TableView<MyScene> tvScenes;

    private ObservableList<MyScene> tvData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tvData = FXCollections.observableArrayList();


        TableColumn<MyScene, Void> column = new TableColumn<>("Custom Column");
        column.setCellFactory(param -> new SceneTableCell());

        tvScenes.getColumns().add(column);

        tvData.add(new MyScene());
        tvScenes.setItems(tvData);

    }
}
