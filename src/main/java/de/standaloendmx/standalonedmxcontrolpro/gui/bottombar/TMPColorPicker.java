package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TMPColorPicker extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        int numColors = 360;
        double radius = 100;
        double centerX = 150;
        double centerY = 150;

        for (int i = 0; i < numColors; i++) {
            double angle = Math.toRadians(i);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            Circle colorCircle = new Circle(x, y, 5, Color.rgb((i * 255 / numColors), 255, 255));
            root.getChildren().add(colorCircle);
        }

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("RGB Circle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}