package de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties;

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
    private TableColumn<TableStep, String> colHoldTime;

    private ObservableList<TableStep> tvData;

    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        step = 0;
        tvData = FXCollections.observableArrayList();

        colPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        colPos.setEditable(false);




        colFade.setCellValueFactory(new PropertyValueFactory<>("fadeTime"));
        colFade.setCellFactory(TextFieldTableCell.forTableColumn());
        colFade.setOnEditCommit(event -> {
            String regex = "\\d{2}:\\d{2}:\\d{2}";
            TableStep step = event.getRowValue();
            if(event.getNewValue().matches(regex)){
                step.setFadeTime(event.getNewValue());
                tvSteps.refresh();
            }else {
                step.setFadeTime(event.getOldValue());
                tvSteps.refresh();
            }
        });
        colFade.setEditable(true); // Diese Spalte ist editierbar



        colHoldTime.setCellValueFactory(new PropertyValueFactory<>("holdTime"));
        colHoldTime.setCellFactory(TextFieldTableCell.forTableColumn());
        colHoldTime.setOnEditCommit(event -> {
            String regex = "\\d{2}:\\d{2}:\\d{2}";
            TableStep step = event.getRowValue();
            if(event.getNewValue().matches(regex)){
                step.setHoldTime(event.getNewValue());
                tvSteps.refresh();
            }else {
                step.setHoldTime(event.getOldValue());
                tvSteps.refresh();
            }
        });
        colHoldTime.setEditable(true); // Diese Spalte ist editierbar
        colHoldTime.setSortable(false);



        tvData.add(new TableStep(0, "00:00:00", "00:01:00"));

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
    private String getLastStepWait(){
        return tvData.get(tvData.size()-1).getHoldTime();
    }
}
