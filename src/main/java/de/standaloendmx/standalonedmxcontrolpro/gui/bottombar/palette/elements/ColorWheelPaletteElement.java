package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.ColorHistoryPane;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.ColorWheel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorWheelPaletteElement extends PaletteElementViewController {

    private RGBColorDisplay rGBColorDisplay;
    private Pane colorDisplay;
    private ColorWheel colorWheel;
    private VBox vBox;
    private HBox colorHistoryBox;

    private ColorHistoryPane[] colorHistoryPanes;


    public ColorWheelPaletteElement() {
        super();
        rGBColorDisplay = new RGBColorDisplay();
        rGBColorDisplay.setColor(Color.WHITE);



        headerBox.getChildren().add(createIconButton("anto-reload"));
        headerBox.getChildren().add(rGBColorDisplay);

        colorDisplay = createColorDisplay();
        vBox = new VBox();
        colorWheel = new ColorWheel();
        colorHistoryBox = new HBox();

        colorHistoryBox.getChildren().add(colorDisplay);

        colorHistoryBox.setSpacing(10);
        colorHistoryPanes = new ColorHistoryPane[5];
        for (int i = 0; i < colorHistoryPanes.length; i++) {
            colorHistoryPanes[i] = new ColorHistoryPane(colorWheel);
            colorHistoryBox.getChildren().add(colorHistoryPanes[i]);
        }

        label.setText("RGB");

        vBox.setSpacing(5);
        vBox.getChildren().add(colorWheel);

        content.getChildren().add(vBox);


        colorWheel.setOnColorChanged(e -> {
            colorDisplay.setBackground(new Background(new BackgroundFill(e.getColor(), new CornerRadii(5), Insets.EMPTY)));
            rGBColorDisplay.setColor(colorWheel.getSelectedColor());
        });

        vBox.getChildren().add(colorHistoryBox);

    }


    //Not called actually why so ever ?!?!
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private Pane createColorDisplay() {
        Pane colorDisplay = new Pane();
        colorDisplay.setPrefSize(30, 30);
        colorDisplay.setMaxWidth(30);
        colorDisplay.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
        return colorDisplay;
    }

    private class RGBColorDisplay extends HBox {
        private Label redLabel;
        private Label greenLabel;
        private Label blueLabel;

        public RGBColorDisplay() {
            // Erstelle Labels
            redLabel = new Label();
            greenLabel = new Label();
            blueLabel = new Label();

            // In HBox hinzufügen
            this.getChildren().addAll(redLabel, greenLabel, blueLabel);

            // Setze den Abstand zwischen den Labels
            this.setSpacing(5);

            // Setze den Hintergrund und das Padding
            this.setStyle("-fx-background-color: transparent; -fx-border-color: grey; -fx-border-radius: 5");
        }

        public void setColor(Color color) {
            // Aktualisiere Labels mit RGB Werten
            redLabel.setText("R:" + String.format("%03d", (int) (color.getRed() * 255)));
            greenLabel.setText("G:" + String.format("%03d", (int) (color.getGreen() * 255)));
            blueLabel.setText("B:" + String.format("%03d", (int) (color.getBlue() * 255)));
        }
    }
}
