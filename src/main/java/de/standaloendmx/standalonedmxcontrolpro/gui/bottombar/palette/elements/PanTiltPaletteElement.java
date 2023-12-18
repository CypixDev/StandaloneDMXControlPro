package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PanTiltPaletteElement extends PaletteElementViewController{

    private static final int CANVAS_SIZE = 200;
    private static final int CENTER_X = CANVAS_SIZE / 2;
    private static final int CENTER_Y = CANVAS_SIZE / 2;

    private final Canvas canvas;
    private final GraphicsContext gc;

    private int panValue;
    private int tiltValue;

    public PanTiltPaletteElement() {
        canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        gc = canvas.getGraphicsContext2D();

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

            drawDot(event.getX(), event.getY());
        });

        content.getChildren().add(root);
    }

    private void drawCoordinateSystem(GraphicsContext gc) {

        // Draw X-axis
        gc.strokeLine(0, CENTER_Y, CANVAS_SIZE, CENTER_Y);
        // Draw Y-axis
        gc.strokeLine(CENTER_X, 0, CENTER_X, CANVAS_SIZE);
    }


    private void drawDot(double x, double y) {
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double distanceFromCenter = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

        double radius = Math.min(centerX, centerY);

        if (distanceFromCenter < radius) {
            // Clear previous marks by redrawing the color wheel
            drawCoordinateSystem(gc);

            gc.setFill(Color.TRANSPARENT);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2.0);

            double dotRadius = 4.0;

            // Zeichne einen Kreis mit transparentem Inneren und schwarzer Border
            gc.fillOval(x - dotRadius, y - dotRadius, 2 * dotRadius, 2 * dotRadius);
            gc.strokeOval(x - dotRadius, y - dotRadius, 2 * dotRadius, 2 * dotRadius);
        }
    }

    private void updateCoordinateSystem(){

    }
}
