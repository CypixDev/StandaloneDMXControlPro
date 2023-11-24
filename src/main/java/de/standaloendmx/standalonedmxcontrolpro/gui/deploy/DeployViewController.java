package de.standaloendmx.standalonedmxcontrolpro.gui.deploy;

import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeployViewController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRefresh;

    @FXML
    private TreeView<String> treeView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.setFocusTraversable(false);
        treeView.setShowRoot(false);

        TreeItem<String> rootItem = new TreeItem<>("Root");
        treeView.setRoot(rootItem);

        btnAdd.setOnAction(e -> {
            openModalPopup(MainApplication.mainStage);
        });
    }
    private void openModalPopup(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/deploy/AddNewInterfaceView.fxml"));
            Parent popupContent = loader.load();
            AddNewInterfaceViewController controller = loader.getController();
            Stage popupStage = new Stage();


            controller.btnCancel.setOnAction(e -> {
                popupStage.close();
            });
            controller.btnSave.setOnAction(e -> {
                popupStage.close();
            });



            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(primaryStage);
            popupStage.initStyle(StageStyle.DECORATED);
            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
