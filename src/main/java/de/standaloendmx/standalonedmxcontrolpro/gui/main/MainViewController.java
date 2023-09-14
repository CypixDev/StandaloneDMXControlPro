package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    public static MainViewController instance;

    @FXML
    public BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;

        try {
            //Parent contentArea = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.CONTENT_AREA);

            Parent contentArea = FXMLLoader.load(getClass().getResource("/gui/main/ContentAreaView.fxml"));
            Parent sideBar = FXMLLoader.load(getClass().getResource("/gui/main/SideBarView.fxml"));
            Parent menuBar = FXMLLoader.load(getClass().getResource("/gui/main/MenuBarView.fxml"));
            Parent bottomBar = FXMLLoader.load(getClass().getResource("/gui/main/BottomBarView.fxml"));
            //Parent bottomBar = (Parent) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.BOTTOM_BAR);
            //AnchorPane sideBarClone = (AnchorPane) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.SIDE_BAR);


            borderPane.setTop(menuBar);
            borderPane.setLeft(sideBar);
            borderPane.setBottom(bottomBar);
            borderPane.setCenter(contentArea);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
