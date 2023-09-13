package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        // Lade die FXML-Datei für den Ladescreen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/loading/LoadingView.fxml"));
        Parent root = loader.load();


        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("StandaloneDMXControlPro");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/gui/img/logo/logo_mini_transparent.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);


        primaryStage.show();

    }
}