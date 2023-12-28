package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ColorHistoryPane extends Pane {

    private final ColorWheel colorWheelInstance;
    private Color color;
    private double x, y;

    public ColorHistoryPane(ColorWheel colorWheelInstance) {
        super();
        this.colorWheelInstance = colorWheelInstance;
        setPrefSize(25, 25);
        setMaxWidth(25);
        setMaxHeight(25);
        //setColor(Color.WHITE, 0, 0);
        setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));

        setOnMouseClicked(e -> { //TODO improve with double click....
            if (color == null) {
                setColor(colorWheelInstance.getSelectedColor(), e.getX(), e.getY());
            } else {
                colorWheelInstance.setSelectedColor(color, x, y);
            }
        });
    }


    public void setColor(Color color, double x, double y) {
        this.color = color;
        this.x = x;
        this.y = y;
        setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), Insets.EMPTY)));
    }

    public Color getColor() {
        return color;
    }
}
