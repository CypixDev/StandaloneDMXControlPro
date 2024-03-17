package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class MainApplication extends Application {

    public static Stage mainStage;

    /**
     * This method initializes and displays the main stage of the application.
     * It sets the title, icon, style, scene, and resize ability of the stage.
     *
     * @param primaryStage the primary stage of the application
     * @throws Exception if an error occurs during stage initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        // Lade die FXML-Datei für den Lade-screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/loading/LoadingView.fxml"));
        Parent root = loader.load();


        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("StandaloneDMXControlPro");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gui/img/logo/logo_mini_transparent.png"))));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);


        primaryStage.show();

    }
}