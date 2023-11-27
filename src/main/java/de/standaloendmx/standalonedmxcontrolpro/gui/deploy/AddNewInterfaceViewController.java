package de.standaloendmx.standalonedmxcontrolpro.gui.deploy;

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
    public Button btnSave;
    @FXML
    public Button btnCancel;
    @FXML
    private ComboBox<MySerialPort> comBox;
    @FXML
    private TextField tvName;
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
            if (tvName.getText().isEmpty() || comBox.getSelectionModel().getSelectedItem() == null) {
                label.setText("Enter a name and select a com port.");
            } else {
                label.setText("");
                saveData(tvName.getText(), "", comBox.getSelectionModel().getSelectedItem().getUuid());
            }
        });
    }

    private void saveData(String name, String group, String uuid) {
        DeployedInterface deployedInterface = new DeployedInterface(name, group, uuid);
        StandaloneDMXControlPro.instance.getDeployedInterfaceManager().saveInFiles(deployedInterface);
        DeployViewController.instance.getTreeView().getRoot().getChildren().add(new TreeItem<DeployedInterface>(deployedInterface));
    }
}
