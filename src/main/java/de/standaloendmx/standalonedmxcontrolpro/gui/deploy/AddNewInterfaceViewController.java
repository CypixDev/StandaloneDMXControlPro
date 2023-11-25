package de.standaloendmx.standalonedmxcontrolpro.gui.deploy;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewInterfaceViewController implements Initializable {

    public static AddNewInterfaceViewController instance;


    @FXML
    private ComboBox<MySerialPort> comBox;

    @FXML
    private TextField tvName;

    @FXML
    public Button btnSave;
    @FXML
    public Button btnCancel;

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        comBox.getItems().addAll(StandaloneDMXControlPro.instance.getSerialServer().getAvailableComPorts());

        comBox.setOnAction(event -> {
            MySerialPort selectedOption = comBox.getSelectionModel().getSelectedItem();
            //setDeviceId(StandaloneDMXControlPro.instance.getSerialServer().getUUID(selectedOption));
        });

        btnSave.setOnAction(e -> {
            if(tvName.getText().isEmpty() || comBox.getSelectionModel().getSelectedItem() == null){
                label.setText("Enter a name and select a com port.");
                saveData();
            }else label.setText("");
        });
    }

    private void saveData() {
        DeployViewController.instance.getTreeView().getRoot().getChildren().add(new TreeItem<DeployedInterface>(new DeployedInterface()));
    }
}
