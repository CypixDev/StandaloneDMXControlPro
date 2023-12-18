package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public class PanTiltPaletteElement extends PaletteElementViewController{

    private static final int CANVAS_SIZE = 200;
    private static final int CENTER_X = CANVAS_SIZE / 2;
    private static final int CENTER_Y = CANVAS_SIZE / 2;

    public PanTiltPaletteElement() {
        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawCoordinateSystem(gc);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        canvas.setOnMouseMoved(event -> {
            double x = event.getX() - CENTER_X;
            double y = CENTER_Y - event.getY(); // Invert Y-axis

            // Normalize values to fit within the range of -1 to 1
            double normalizedX = x / CENTER_X;
            double normalizedY = y / CENTER_Y;

            // Print normalized coordinates (you can use these values for your moving head)
            System.out.println("Normalized Coordinates: X=" + normalizedX + ", Y=" + normalizedY);
        });

        content.getChildren().add(root);
    }

    private void drawCoordinateSystem(GraphicsContext gc) {
        // Draw X-axis
        gc.strokeLine(0, CENTER_Y, CANVAS_SIZE, CENTER_Y);
        // Draw Y-axis
        gc.strokeLine(CENTER_X, 0, CENTER_X, CANVAS_SIZE);
    }
}
