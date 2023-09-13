package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import de.standaloendmx.standalonedmxcontrolpro.fixture.Fixture;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureMode;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class PatchGridViewController implements Initializable {

    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int counter = 1;
        Label label;
        Pane pane;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 32; j++) {
                pane = new Pane();
                pane.setPrefHeight(30);
                pane.setPrefWidth(30);

                label = new Label(String.valueOf(counter));

                pane.getChildren().add(label);
                label.layoutXProperty().bind(pane.widthProperty().subtract(label.widthProperty()).divide(2));
                label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));

                pane.setOnDragEntered(e -> {
                    //getPaneAtXY(e.getSceneX(),e.getSceneY()).setStyle("-fx-background-color: -fx-accent-color;");
                });

                pane.setOnDragExited(e -> {
                    for (Node child : grid.getChildren()) {
                        if (!child.getStyle().contains("-fx-grey3")) {
                            child.setStyle("-fx-background-color: -fx-color;");
                        }
                    }
                });

                grid.add(pane, j, i);
                counter++;
            }
        }

        grid.setOnDragEntered(e -> {
            //e.getDragboard().set
        });

        grid.setOnDragOver(e -> {
            if (e.getGestureSource() != grid && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);

                colorPane(e);
            }
            e.consume();
        });

        grid.setOnDragDone(e -> {
            getPaneAtXY(e.getX(), e.getY()).setStyle("-fx-background-color: -fx-grey3;");
        });

        grid.setOnDragDropped(this::colorPane);

    }

    private void colorPane(DragEvent e) {
        String[] data = e.getDragboard().getString().split(":");
        String fixtureName = data[0];
        Fixture fixture = StandaloneDMXControlPro.instance.getFixtureManager().getFixtureByName(fixtureName);
        FixtureMode mode;
        if(data.length == 2){
            String modeName = data[1];
            mode = fixture.getModeByName(modeName);
        }else mode = fixture.getModes().get(0); //In case there is only one

        int size = mode.getFixtureChannels().size();

        int pos = getPaneAtXYPos(e.getX(), e.getY());
        if (pos + size <= 513) {
            for (int i = 0; i < size; i++) {
                grid.getChildren().get(pos + i).setStyle("-fx-background-color: -fx-grey3;");
            }
        }
    }

    //SOOOO FUCKING GREAT!!!!
    private Pane getPaneAtXY(double x, double y) {
        return (Pane) grid.getChildren().get(getPaneAtXYPos(x, y));
    }

    private int getPaneAtXYPos(double x, double y) {
        double width = ((Pane) grid.getChildren().get(1)).getWidth();
        double height = ((Pane) grid.getChildren().get(1)).getHeight();
        int betterX = (int) (x / width) + 1;
        int betterY = (int) (y / height);
        int pos = betterX + betterY * 32;
        if (pos > 512) pos = 512;
        return pos;
    }
}
