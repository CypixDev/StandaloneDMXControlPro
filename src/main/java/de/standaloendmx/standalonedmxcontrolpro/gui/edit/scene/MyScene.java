package de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene;

import de.standaloendmx.standalonedmxcontrolpro.gui.deploy.DeployedInterface;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.ScenesViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.group.GroupColor;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties.StepsViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties.TableStep;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.serial.SerialServer;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.ScenePacket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

public class MyScene extends VBox implements Initializable {

    private Logger logger = LogManager.getLogger(MyScene.class);

    private UUID uuid;

    @FXML
    private VBox container;

    @FXML
    public Label name;

    @FXML
    private Label time;

    @FXML
    private Label repeat;

    private GroupColor color;

    private ObservableList<TableStep> steps;

    public MyScene(GroupColor color) {
        uuid = UUID.randomUUID();
        steps = FXCollections.observableArrayList();
        steps.add(new TableStep(0, "00:00:00", "00:01:00", new HashMap<>()));

        this.color = color;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/edit/scene/MySceneView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getStyleClass().add(color.getBorderStyleClassName());

        container.setOnMouseClicked(event -> {
            if (container.getStyleClass().contains("scene_selected")) {
                container.getStyleClass().remove("scene_selected");
            } else {
                if (!event.isControlDown()) {
                    ScenesViewController.instance.unselectAllScenes();
                }
                container.getStyleClass().add("scene_selected");
            }
            StepsViewController.instance.update();
            StepsViewController.instance.selectFirstStep();
        });

        container.setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem menuItem = new MenuItem("Send to Device");
            //TODO: fix / remove?
            menuItem.setOnAction(event -> {
                System.out.println("Sending...");
                System.out.println("Size: "+StandaloneDMXControlPro.instance.getDeployedInterfaceManager().getDeployedInterfaceList().size());
                if(StandaloneDMXControlPro.instance.getDeployedInterfaceManager().getDeployedInterfaceList().get(0).getSerialPort() != null){
                    DeployedInterface deployedInterface = StandaloneDMXControlPro.instance.getDeployedInterfaceManager().getDeployedInterfaceList().get(0);
                    try {
                        SerialServer.getInstance().writeAndFlushPacket(deployedInterface.getSerialPort(), new ScenePacket(this));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            contextMenu.getItems().add(menuItem);

            this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenu.show(MyScene.this, event.getScreenX(), event.getScreenY());
                }
            });
        });
    }

    public GroupColor getColor() {
        return color;
    }

    public ObservableList<TableStep> getSteps() {
        return steps;
    }

    public UUID getUuid() {
        return uuid;
    }
}
