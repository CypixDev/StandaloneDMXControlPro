package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.elements;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.ColorHistoryPane;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.palette.ColorWheel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class ColorWheelPaletteElement extends PaletteElementViewController {

    private Pane colorDisplay;
    private ColorWheel colorWheel;
    private VBox vBox;
    private HBox colorHistoryBox;

    private ColorHistoryPane[] colorHistoryPanes;


    public ColorWheelPaletteElement() {
        super();



        headerBox.getChildren().add(createIconButton());

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
        });

        vBox.getChildren().add(colorHistoryBox);

    }


    //Not called actually why so ever ?!?!
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Button createIconButton() {
        Button btn = new Button();
        FontIcon icon = new FontIcon();
        icon.setIconLiteral("anto-reload");
        btn.setGraphic(icon);
        return btn;
    }
    private Pane createColorDisplay() {
        Pane colorDisplay = new Pane();
        colorDisplay.setPrefSize(30, 30);
        colorDisplay.setMaxWidth(30);
        colorDisplay.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
        return colorDisplay;
    }

}
