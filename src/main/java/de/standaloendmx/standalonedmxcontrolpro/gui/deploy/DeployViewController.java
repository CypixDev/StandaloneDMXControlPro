package de.standaloendmx.standalonedmxcontrolpro.gui.deploy;

import de.standaloendmx.standalonedmxcontrolpro.files.FileUtils;
import de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeployViewController implements Initializable {

    public static DeployViewController instance;

    private final Logger logger = LogManager.getLogger(DeployViewController.class);

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRefresh;

    @FXML
    private TreeView<DeployedInterface> treeView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        treeView.setFocusTraversable(false);
        treeView.setShowRoot(false);

        TreeItem<DeployedInterface> rootItem = new TreeItem<>(new DeployedInterface());
        treeView.setRoot(rootItem);
        rootItem.getChildren().add(new TreeItem<>(new DeployedInterface()));

        btnAdd.setOnAction(e -> {
            openModalPopup(MainApplication.mainStage);
        });

        treeView.setCellFactory(param -> new TreeCell<DeployedInterface>() {
            @Override
            protected void updateItem(DeployedInterface deployedInterface, boolean empty) {
                super.updateItem(deployedInterface, empty);
                if (deployedInterface == null || empty) {
                    setGraphic(null);
                } else {
                    setText(deployedInterface.getName());
                    String svg = FileUtils.getSVGPath("/gui/img/icons/remote.svg");

                    SVGPath svgPath = new SVGPath();
                    svgPath.setContent(svg);

                    svgPath.setFill(Color.BLACK);
                    try {
                        setGraphic(svgPath);
                    } catch (Exception e) {
                        logger.error("Error while setting tree-item graphic", e);
                    }
                }
            }
        });


        treeView.setOnContextMenuRequested(event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem openMenuItem = new MenuItem("Öffnen");
            contextMenu.getItems().add(openMenuItem);

            TreeItem<DeployedInterface> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                contextMenu.show(treeView, event.getScreenX(), event.getScreenY());

                openMenuItem.setOnAction(actionEvent -> {
                    System.out.println("Open fixture info...");
                });
            }
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


            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(primaryStage);
            popupStage.initStyle(StageStyle.DECORATED);
            popupStage.setScene(new Scene(popupContent));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public TreeView<DeployedInterface> getTreeView() {
        return treeView;
    }
}
