package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StepsViewController implements Initializable {

    @FXML
    private TableView<TableStep> tvSteps;

    @FXML
    private TableColumn<TableStep, Integer> colPos;
    @FXML
    private TableColumn<TableStep, Integer> colFade;
    @FXML
    private TableColumn<TableStep, Integer> colWait;

    private ObservableList<TableStep> tvData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvData = FXCollections.observableArrayList();

        colPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        colFade.setCellValueFactory(new PropertyValueFactory<>("fadeTime"));
        colWait.setCellValueFactory(new PropertyValueFactory<>("waitTime"));

        tvData.add(new TableStep(0, 22, 222));

        tvSteps.setItems(tvData);
    }
}
