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

    // Simulierte Ladedauer (in Millisekunden)
    private final int LADEDAUER_MS = 5000/2;

    public void initialize() {
        // Initialisieren Sie die Fortschrittseigenschaften
        //progressBar.setProgress(0);
        //nachrichtLabel.setText("Starte den Ladevorgang...");

        // Starten Sie den Ladevorgang
        startLadevorgang();
    }

    private void startLadevorgang() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(LADEDAUER_MS / 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int fortgeschritten = i;
                // Aktualisieren Sie den Fortschritt auf dem JavaFX-Thread
/*                javafx.application.Platform.runLater(() -> {

*//*                    progressBar.setProgress(fortgeschritten / 100.0);
                    nachrichtLabel.setText("Ladevorgang: " + fortgeschritten + "%");*//*
                });*/
            }
            // Ladevorgang abgeschlossen
            //nachrichtLabel.setText("Alles geladen!");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        BorderPane pane = new FXMLLoader(LoadingScreenController.class.getResource(
                                "/gui/main/MainView.fxml")).load();
                        MainApplication.mainStage.setScene(new Scene(pane));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        });
        thread.start();
    }
}