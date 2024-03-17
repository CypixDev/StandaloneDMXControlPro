package de.standaloendmx.standalonedmxcontrolpro.gui.loading;

import de.standaloendmx.standalonedmxcontrolpro.gui.Views;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

public class LoadingScreenController {

    private final int LADEDAUER = 5000 / 10; //remove ?

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label nachrichtLabel;

    public void initialize() {
        startDelay();
    }

    /**
     * Starts a delay in a separate thread.
     * This method is responsible for loading fixtures, loading views,
     * and setting up the main stage of the application.
     */
    private void startDelay() {
        Thread thread = new Thread(() -> {

            Platform.runLater(() -> nachrichtLabel.setText("Lade Fixtures"));
            StandaloneDMXControlPro.instance.getFixtureManager().loadAllFixturesFromFiles();


            Platform.runLater(() -> nachrichtLabel.setText("Lade Views"));
            StandaloneDMXControlPro.instance.getViewManager().loadAllViews();

            //TODO remove ? just testing...
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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