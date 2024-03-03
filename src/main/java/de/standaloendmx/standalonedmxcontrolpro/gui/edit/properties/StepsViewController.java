package de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.FaderViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.MyFader;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.ScenesViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StepsViewController implements Initializable {

    public static StepsViewController instance;

    private int step;

    @FXML
    private TableView<TableStep> tvSteps;

    @FXML
    private TableColumn<TableStep, Integer> colPos;
    @FXML
    private TableColumn<TableStep, String> colFade;
    @FXML
    private TableColumn<TableStep, String> colHoldTime;

    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        step = 0;


        tvSteps.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                onStepClick();
            }
        });


        colPos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        colPos.setEditable(false);


        colFade.setCellValueFactory(new PropertyValueFactory<>("fadeTime"));
        colFade.setCellFactory(TextFieldTableCell.forTableColumn());
        colFade.setOnEditCommit(event -> {
            String regex = "\\d{2}:\\d{2}:\\d{2}";
            TableStep step = event.getRowValue();
            if (event.getNewValue().matches(regex)) {
                step.setFadeTime(event.getNewValue());
                tvSteps.refresh();
            } else {
                step.setFadeTime(event.getOldValue());
                tvSteps.refresh();
            }
        });
        colFade.setEditable(true);


        colHoldTime.setCellValueFactory(new PropertyValueFactory<>("holdTime"));
        colHoldTime.setCellFactory(TextFieldTableCell.forTableColumn());
        colHoldTime.setOnEditCommit(event -> {
            String regex = "\\d{2}:\\d{2}:\\d{2}";
            TableStep step = event.getRowValue();
            if (event.getNewValue().matches(regex)) {
                step.setHoldTime(event.getNewValue());
                tvSteps.refresh();
            } else {
                step.setHoldTime(event.getOldValue());
                tvSteps.refresh();
            }
        });
        colHoldTime.setEditable(true);
        colHoldTime.setSortable(false);


        tvSteps.setEditable(true);
        btnAdd.setOnAction(e -> {
            if(!tvSteps.getSelectionModel().getSelectedCells().isEmpty()){
                step++;
                tvSteps.getItems().add(new TableStep(step, getLastStepFade(), getLastStepWait(), getLastChannelValues()));
                tvSteps.getSelectionModel().select(tvSteps.getItems().size() - 1);
            }
        });
    }

    private void onStepClick() {
        System.out.print("Step Clicked. ");
        if (!tvSteps.getSelectionModel().isEmpty()) {
            TableStep selectedData = tvSteps.getSelectionModel().getSelectedItem();
            System.out.println("Actually selected no. "+selectedData.getPos());
            System.out.println("    "+ selectedData.getChannelValues().toString());
            FaderViewController.instance.setSliders(selectedData.getChannelValues());
            FaderViewController.instance.updateSliders();
        }
    }

    private Map<Integer, Byte> getLastChannelValues() {
        if (tvSteps.getItems().isEmpty()) return new HashMap<>();
        return new HashMap<>(tvSteps.getItems().get(tvSteps.getItems().size() - 1).getChannelValues());
    }

    private String getLastStepFade() {
        if (tvSteps.getItems().isEmpty()) return "00:00:00";
        return tvSteps.getItems().get(tvSteps.getItems().size() - 1).getFadeTime();
    }

    private String getLastStepWait() {
        if (tvSteps.getItems().isEmpty()) return "00:01:00";
        return tvSteps.getItems().get(tvSteps.getItems().size() - 1).getHoldTime();
    }

    public void update() {
        if (ScenesViewController.instance.getSelectedScene() == null) {
            tvSteps.setItems(null);
            FaderViewController.instance.blindAllFaders();
        } else {
            tvSteps.setItems(ScenesViewController.instance.getSelectedScene().getSteps());
        }
        tvSteps.refresh();
    }

    public TableStep getSelectedTableStep() {
        return tvSteps.getSelectionModel().getSelectedItem();
    }

    public void channelValueUpdate(Label channel, Label value) {
        if (getSelectedTableStep() != null){
            byte byteValue = (byte) Integer.parseInt(value.getText());
            getSelectedTableStep().getChannelValues().put(Integer.parseInt(channel.getText()) - 1, byteValue);
        }else System.out.println("No step selected!");
    }

    public void selectFirstStep() {
        if (tvSteps.getItems() != null) {
            tvSteps.getSelectionModel().select(0);
            onStepClick();
        }
    }

    public void buttonActiveUpdate(MyFader myFader) {
        getSelectedTableStep().getChannelValues().remove(Integer.parseInt(myFader.channel.getText())-1);
        System.out.println("Removed "+myFader.channel.getText());
    }
}
