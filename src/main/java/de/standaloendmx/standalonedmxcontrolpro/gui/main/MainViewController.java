package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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


            Parent menuBar = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.MENU_BAR)/* FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/MenuBarView.fxml")))*/;
            Parent sideBar = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.SIDE_BAR)/*FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/SideBarView.fxml")))*/;
            Parent bottomBar = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.MAIN_BOTTOM_BAR)/*FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/BottomBarView.fxml")))*/;
            Parent contentArea = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main/ContentAreaView.fxml")));
            StandaloneDMXControlPro.instance.getViewManager().setLoadedView(Views.CONTENT_AREA, contentArea);

            borderPane.setTop(menuBar);
            borderPane.setLeft(sideBar);
            borderPane.setBottom(bottomBar);
            borderPane.setCenter(contentArea);


        } catch (Exception e) {
            logger.error(e);
        }
    }
}
