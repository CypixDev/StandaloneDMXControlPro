package de.standaloendmx.standalonedmxcontrolpro.gui.edit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class StepsViewController implements Initializable {

    private int step;

    @FXML
    private TableView<TableStep> tvSteps;

    @FXML
    private TableColumn<TableStep, Integer> colPos;
    @FXML
    private TableColumn<TableStep, String> colFade;
    @FXML
    private TableColumn<TableStep, Integer> colWait;

    private ObservableList<TableStep> tvData;

    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        step = 0;
        tvData = FXCollections.observableArrayList();

        colPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        colPos.setEditable(false);
        colWait.setSortable(true);
        /*colFade.setCellValueFactory(new PropertyValueFactory<>("fadeTime"));
        colFade.setEditable(true);
        colFade.setSortable(false);*/
        colWait.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        colWait.setEditable(true);
        colWait.setSortable(false);


        //test
        colFade.setCellValueFactory(new PropertyValueFactory<>("fadeTime"));
        colFade.setCellFactory(TextFieldTableCell.forTableColumn());
        colFade.setOnEditCommit(event -> {
            TableStep step = event.getRowValue();
            step.setFadeTime(event.getNewValue());
        });
        colFade.setEditable(true); // Diese Spalte ist editierbar


        tvData.add(new TableStep(0, "22", 222));

        tvSteps.setItems(tvData);
        tvSteps.setEditable(true);
        btnAdd.setOnAction(e -> {
            step++;
            tvData.add(new TableStep(step, getLastStepFade(), getLastStepWait()));
        });
    }

    private String getLastStepFade(){
        return tvData.get(tvData.size()-1).getFadeTime();
    }
    private int getLastStepWait(){
        return tvData.get(tvData.size()-1).getWaitTime();
    }
}
