package de.standaloendmx.standalonedmxcontrolpro.gui.loading;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class LoadingScreenController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label nachrichtLabel;

    // Simulierte Ladedauer (in Millisekunden)
    private final int LADEDAUER_MS = 5000;

    public void initialize() {
        // Initialisieren Sie die Fortschrittseigenschaften
        //progressBar.setProgress(0);
        //nachrichtLabel.setText("Starte den Ladevorgang...");

        // Starten Sie den Ladevorgang
        //startLadevorgang();
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
                javafx.application.Platform.runLater(() -> {
                    progressBar.setProgress(fortgeschritten / 100.0);
                    nachrichtLabel.setText("Ladevorgang: " + fortgeschritten + "%");
                });
            }
            // Ladevorgang abgeschlossen
            nachrichtLabel.setText("Ladevorgang abgeschlossen!");
        });
        thread.start();
    }
}