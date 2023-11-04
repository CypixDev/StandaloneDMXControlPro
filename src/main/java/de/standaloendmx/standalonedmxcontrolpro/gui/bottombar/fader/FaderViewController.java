package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.MySlider;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FaderViewController implements Initializable {


    private List<MySlider> mySliders;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mySliders = new ArrayList<>();


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 512; i++) {
                    hBox.getChildren().add(new MySlider(i + 1));
                }
            }
        });

        //96+5+71
        scrollPane.prefWidthProperty().bind(MainApplication.mainStage.widthProperty().subtract(96+5+71));
        scrollPane.setContent(hBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Zeige die horizontale Scrollbar immer an
        scrollPane.setFitToWidth(true); // Passe den Inhalt der ScrollPane an die Breite an
        scrollPane.setFitToHeight(true);

    }
}
