package de.standaloendmx.standalonedmxcontrolpro.gui.deploy;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewInterfaceViewController implements Initializable {

    public static AddNewInterfaceViewController instance;


    @FXML
    private ComboBox<MySerialPort> comBox;

    @FXML
    private TextField tvName;

    @FXML
    private Label deviceId;

    @FXML
    public Button btnSave;
    @FXML
    public Button btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        comBox.getItems().addAll(StandaloneDMXControlPro.instance.getSerialServer().getAvailableComPorts());

        comBox.setOnAction(event -> {
            MySerialPort selectedOption = comBox.getSelectionModel().getSelectedItem();
            StandaloneDMXControlPro.instance.getSerialServer().startCom(selectedOption);

        });
    }

    public void setDeviceId(String id){
        deviceId.setText("Interface ID: "+id);
    }
}
