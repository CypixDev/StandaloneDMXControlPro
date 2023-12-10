package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.ColorWheel;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorWheelPaletteElement extends PaletteElementViewController {

    private ColorWheel colorWheel;
    private VBox vBox;


    public ColorWheelPaletteElement() {
        super();
        vBox = new VBox();
        colorWheel = new ColorWheel();

        label.setText("RGB");

        vBox.setSpacing(5);

        content.getChildren().add(vBox);
        vBox.getChildren().add(colorWheel);

        Pane colorDisplay = new Pane();
        colorDisplay.setPrefSize(30, 30);
        colorDisplay.setMaxWidth(30);
        colorDisplay.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));

        colorWheel.setOnColorChanged(e -> {
            colorDisplay.setBackground(new Background(new BackgroundFill(e.getColor(), new CornerRadii(5), Insets.EMPTY)));
        });


        vBox.getChildren().add(colorDisplay);

    }


    //Not called actually why so ever ?!?!
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
