package de.standaloendmx.standalonedmxcontrolpro.gui.loading;

import de.standaloendmx.standalonedmxcontrolpro.main.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class LoadingScreenController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label nachrichtLabel;

    private final int LADEDAUER = 5000/2;

    public void initialize() {
        startDelay();
    }

    private void startDelay() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(LADEDAUER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                try {
                    BorderPane pane = new FXMLLoader(LoadingScreenController.class.getResource(
                            "/gui/main/MainView.fxml")).load();
                    MainApplication.mainStage.setScene(new Scene(pane));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        });
        thread.start();
    }
}