package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

        try{
            Parent sideBar = FXMLLoader.load(getClass().getResource("/gui/main/SideBarView.fxml"));
            Parent menuBar = FXMLLoader.load(getClass().getResource("/gui/main/MenuBarView.fxml"));
            Parent contentArea = FXMLLoader.load(getClass().getResource("/gui/main/ContentAreaView.fxml"));

            borderPane.setTop(menuBar);
            borderPane.setLeft(sideBar);
            borderPane.setCenter(contentArea);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
