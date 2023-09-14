package de.standaloendmx.standalonedmxcontrolpro.gui.loading;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

import java.io.IOException;

public class LoadingScreenController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label nachrichtLabel;

    private final int LADEDAUER = 5000/10;

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
            Platform.runLater(() -> nachrichtLabel.setText("Lade Fixtures"));
            StandaloneDMXControlPro.instance.getFixtureManager().loadAllFixturesFromFiles();

            try {
                Thread.sleep(LADEDAUER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> nachrichtLabel.setText("Lade Views"));
            StandaloneDMXControlPro.instance.getViewManager().loadAllViews();

            try {
                Thread.sleep(LADEDAUER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {

                BorderPane pane = (BorderPane) StandaloneDMXControlPro.instance.getViewManager().getLoadedView(Views.MAIN);

                MainApplication.mainStage.setScene(new Scene(pane));

                //Set full screen
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getBounds();
                MainApplication.mainStage.setWidth(screen.getBounds().getWidth());
                MainApplication.mainStage.setHeight(screen.getBounds().getHeight());

                MainApplication.mainStage.setX(bounds.getMinX() + (bounds.getWidth() - MainApplication.mainStage.getWidth()) / 2);
                MainApplication.mainStage.setY(bounds.getMinY() + (bounds.getHeight() - MainApplication.mainStage.getHeight()) / 2);
            });

        });
        thread.start();
    }
}