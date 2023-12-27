package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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

        canvas.setOnMouseDragged(event -> {
            double x = event.getX() - CENTER_X;
            double y = CENTER_Y - event.getY(); // Invert Y-axis

            // Normalize values to fit within the range of -1 to 1
            double normalizedX = x / CENTER_X;
            double normalizedY = y / CENTER_Y;

            // Print normalized coordinates (you can use these values for your moving head)
            //System.out.println("Normalized Coordinates: X=" + normalizedX + ", Y=" + normalizedY);
            firePanTiltChangedEvent(normalizedX, normalizedY);

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
        if (x < 0 || y < 0 || x > canvas.getWidth() || y > canvas.getHeight()) {
            // The point is outside the canvas, so we ignore this
            return;
        }

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Redraw the coordinate system
        drawCoordinateSystem(gc);

        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);

        double dotRadius = 4.0;

        // Draw a circle with a transparent center and black border
        gc.fillOval(x - dotRadius, y - dotRadius, 2 * dotRadius, 2 * dotRadius);
        gc.strokeOval(x - dotRadius, y - dotRadius, 2 * dotRadius, 2 * dotRadius);
    }

    private List<EventHandler<PanTiltChangeEvent>> panTiltChangedHandlers = new ArrayList<>();
    public void setOnPanTiltChanged(EventHandler<PanTiltChangeEvent> handler) {
        addPanTiltChangedHandler(handler);
    }
    public void addPanTiltChangedHandler(EventHandler<PanTiltChangeEvent> handler) {
        panTiltChangedHandlers.add(handler);
    }

    private void firePanTiltChangedEvent(double x, double y) {
        PanTiltChangeEvent event = new PanTiltChangeEvent(x, y);
        for (EventHandler<PanTiltChangeEvent> handler : panTiltChangedHandlers) {
            handler.handle(event);
        }
    }

    public static class PanTiltChangeEvent extends ActionEvent{

        private double x;
        private double y;

        public PanTiltChangeEvent(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

}
