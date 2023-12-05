package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.awt.*;

public class ColorWheelControl extends Control {

    private Canvas colorWheelCanvas;
    private GraphicsContext colorWheelGC;

    public ColorWheelControl() {
        initialize();
    }

    private void initialize() {
        colorWheelCanvas = new Canvas(200, 200); //The actual canvas, nearly the size of the circle
        colorWheelGC = colorWheelCanvas.getGraphicsContext2D();

        drawColorWheel();

        Pane root = new Pane();
        root.setStyle("-fx-background-color: lime;");
        root.getChildren().add(colorWheelCanvas);

        // Set up event listeners for mouse click and drag
        colorWheelCanvas.setOnMouseClicked(event -> {
            handleColorSelection(event.getX(), event.getY());
        });

        colorWheelCanvas.setOnMouseDragged(event -> {
            handleColorSelection(event.getX(), event.getY());
        });

        getChildren().add(root);
    }

    private void handleColorSelection(double mouseX, double mouseY) {
        Color selectedColor = getColorAtMousePosition(mouseX, mouseY);
        if (selectedColor != null) {
            System.out.println("Selected Color: " + selectedColor);

            // Print RGB values
            printRGBValues(selectedColor);

            // Draw a mark at the current mouse position
            drawMark(mouseX, mouseY);
        }
    }

    private Color getColorAtMousePosition(double mouseX, double mouseY) {
        int x = (int) mouseX;
        int y = (int) mouseY;

        // Check if the mouse is within the canvas bounds
        if (x < 0 || y < 0 || x >= colorWheelCanvas.getWidth() || y >= colorWheelCanvas.getHeight()) {
            return null;
        }

        // Directly retrieve color from the canvas
        return colorWheelCanvas.snapshot(null, null).getPixelReader().getColor(x, y);
    }

    private void drawMark(double x, double y) {
        // Clear previous marks by redrawing the color wheel
        drawColorWheel();

        colorWheelGC.setFill(Color.TRANSPARENT);
        colorWheelGC.setStroke(Color.BLACK);
        colorWheelGC.setLineWidth(2.0);

        double dotRadius = 4.0;

        // Zeichne einen Kreis mit transparentem Inneren und schwarzer Border
        colorWheelGC.fillOval(x - dotRadius, y - dotRadius, 2 * dotRadius, 2 * dotRadius);
        colorWheelGC.strokeOval(x - dotRadius, y - dotRadius, 2 * dotRadius, 2 * dotRadius);

    }

    private void drawColorWheel() {
        double centerX = colorWheelCanvas.getWidth() / 2;
        double centerY = colorWheelCanvas.getHeight() / 2;
        double radius = Math.min(centerX, centerY);

// Setze die Farbe für den gesamten Hintergrund des Canvas
        colorWheelGC.setFill(Color.GREY);
        colorWheelGC.fillRect(0, 0, colorWheelCanvas.getWidth(), colorWheelCanvas.getHeight());

        for (double hue = 0; hue < 360; hue += 0.1) {
            Color color = Color.hsb(hue, 1.0, 1.0);
            double angle = Math.toRadians(hue);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            colorWheelGC.setStroke(color);
            colorWheelGC.strokeLine(centerX, centerY, x, y);
        }

        RadialGradient gradient = new RadialGradient(0, 0, centerX, centerY, radius, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.TRANSPARENT)
        );

        colorWheelGC.setFill(gradient);
        colorWheelGC.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

    }

    private void printRGBValues(Color color) {
        int red = (int) Math.round(color.getRed() * 255);
        int green = (int) Math.round(color.getGreen() * 255);
        int blue = (int) Math.round(color.getBlue() * 255);

        System.out.println("Red: " + red + ", Green: " + green + ", Blue: " + blue);
    }
}
