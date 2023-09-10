package de.standaloendmx.standalonedmxcontrolpro.main;

import de.standaloendmx.standalonedmxcontrolpro.gui.loading.LoadingScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Lade die FXML-Datei für den Ladescreen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/loading/LoadingScreen.fxml"));
        Parent root = loader.load();


        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Ladebildschirm Beispiel");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);


        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}