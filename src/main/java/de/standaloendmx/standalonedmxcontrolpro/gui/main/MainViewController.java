package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(MainViewController.class);

    public static MainViewController instance;

    @FXML
    public BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        try {
            //Parent contentArea = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.CONTENT_AREA);

            Parent contentArea = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/ContentAreaView.fxml")));
            Parent sideBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/SideBarView.fxml")));
            Parent menuBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/MenuBarView.fxml")));
            Parent bottomBar = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/BottomBarView.fxml")));
            //Parent bottomBar = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.BOTTOM_BAR);
            //AnchorPane sideBarClone = (AnchorPane) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.SIDE_BAR);


            borderPane.setTop(menuBar);
            borderPane.setLeft(sideBar);
            borderPane.setBottom(bottomBar);
            borderPane.setCenter(contentArea);


        } catch (IOException e) {
            logger.error(e);
        }
    }
}
