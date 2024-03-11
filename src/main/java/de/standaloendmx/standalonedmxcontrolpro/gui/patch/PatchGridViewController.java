package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureMode;
import de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatchGridViewController implements Initializable {

    public static PatchGridViewController instance;

    @FXML
    private GridPane grid;

    private PatchManager patchManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        patchManager = StandaloneDMXControlPro.instance.getPatchManager();

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

              /*Future:
                   pane.setOnDragDetected(e -> {

                    if(getChannelByPane((Pane) e.getTarget()) != -1){
                        Dragboard dragboard = ((Pane) e.getTarget()).startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        System.out.println("Correct target");
                    }
                });*/
                /*
                        directory.setOnDragDetected(event -> {
            TreeItem<Fixture> selectedItem = directory.getSelectionModel().getSelectedItem();
            if(selectedItem != null && selectedItem.isLeaf()){ //So that folders cannot be moved
                Dragboard dragboard = directory.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();

                if(selectedItem.getValue().isMode()){
                    Fixture item = selectedItem.getParent().getValue();
                    String modeName = selectedItem.getValue().getName();
                    content.put(DataFormat.PLAIN_TEXT, item.getName()+":"+modeName);

                }else{
                    Fixture item = selectedItem.getValue();
                    content.put(DataFormat.PLAIN_TEXT,  item.getName());
                }


                dragboard.setContent(content);
                event.consume();
            }
        });
                 */

                pane.setOnDragExited(e -> {
                    int size = getFixtureSizeByDragBoardString(e.getDragboard().getString());
                    Pane pane1 = (Pane) e.getTarget();
                    pane1.setStyle("-fx-background-color: -fx-color;");

                    for (int i1 = 0; i1 < grid.getChildren().size(); i1++) {
                        if (grid.getChildren().get(i1).equals(pane1)) {
                            for (int i2 = 0; i2 < size; i2++) {
                                if (i1 + i2 < 512)
                                    grid.getChildren().get(i1 + i2).setStyle("-fx-background-color: -fx-color;");
                            }
                            break;
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
            //getPaneAtXY(e.getX(), e.getY()).setStyle("-fx-background-color: -fx-grey3;");
        });

        grid.setOnDragDropped(e -> {
            PatchFixture patchFixture = getFixtureByDragBoardString(e.getDragboard().getString());
            int pos = getPaneAtXYPos(e.getX(), e.getY());

            String[] data = e.getDragboard().getString().split(":");
            FixtureMode mode;
            if (data.length == 2) {
                String modeName = data[1];
                mode = patchFixture.getModeByName(modeName);
            } else mode = patchFixture.getModes().get(0); //In case there is only one

            if (StandaloneDMXControlPro.instance.getPatchManager().isChannelFree(pos, mode.getFixtureChannels().size())) {
                StandaloneDMXControlPro.instance.getPatchManager().addPatch(new de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture(patchFixture, pos, mode.getFixtureChannels().size(), Color.LIME.toString()));
            }

            updatePatch();
            //colorPane(e);
        });

        updatePatch();

    }

    public void updatePatch() {
        for (de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture patchPatch : patchManager.getPatches()) {
            applyPatch(patchPatch);
        }
    }

    private void applyPatch(de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture patch) {
        Pane pane = (Pane) grid.getChildren().get(patch.getChannel());

        pane.setOnMouseClicked(e -> {

            //removePatch(patch);
        });
        pane.setOnContextMenuRequested(e -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Remove");
            removeItem.setOnAction(b -> {
                removePatch(StandaloneDMXControlPro.instance.getPatchManager().getPatchByChannel(getChannelByPane(pane)));
                StandaloneDMXControlPro.instance.getPatchManager().removePatch(getChannelByPane(pane));
            });

            contextMenu.getItems().add(removeItem);
            contextMenu.show(pane, e.getScreenX(), e.getScreenY());
        });

        GridPane.setColumnSpan(pane, patch.getSize());
        for (int i = 1; i < patch.getSize(); i++) {
            grid.getChildren().get(patch.getChannel() + i).setVisible(false);
        }
        pane.setBorder(new Border(new BorderStroke(Color.valueOf(patch.getColor()), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        ((Label) pane.getChildren().get(0)).setText(patch.getName());
    }

    private int getChannelByPane(Pane pane) {
        int channel = 0;
        for (Node child : grid.getChildren()) {
            if (pane.equals(child)) return channel;
            channel++;
        }
        return -1;
    }

    private void removePatch(de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture patch) {
        GridPane.setColumnSpan(grid.getChildren().get(patch.getChannel()), 1);
        for (int i = 1; i < patch.getSize(); i++) {
            grid.getChildren().get(patch.getChannel() + i).setVisible(true);
        }
        ((Pane) grid.getChildren().get(patch.getChannel())).setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        ((Label) ((Pane) grid.getChildren().get(patch.getChannel())).getChildren().get(0)).setText(String.valueOf(patch.getChannel()));
    }

    private void colorPane(DragEvent e) {
        String[] data = e.getDragboard().getString().split(":");
        String fixtureName = data[0];
        PatchFixture patchFixture = StandaloneDMXControlPro.instance.getFixtureManager().getFixtureByName(fixtureName);
        FixtureMode mode;
        if (data.length == 2) {
            String modeName = data[1];
            mode = patchFixture.getModeByName(modeName);
        } else mode = patchFixture.getModes().get(0); //In case there is only one

        int size = mode.getFixtureChannels().size();

        int pos = getPaneAtXYPos(e.getX(), e.getY());
        if (pos + size <= 512 && StandaloneDMXControlPro.instance.getPatchManager().isChannelFree(pos, size)) {
            for (int i = 0; i < size; i++) {
                grid.getChildren().get(pos + i).setStyle("-fx-background-color: -fx-grey3;");
            }
        }
    }


    private PatchFixture getFixtureByDragBoardString(String dragBoardString) {
        String[] data = dragBoardString.split(":");
        String fixtureName = data[0];
        return StandaloneDMXControlPro.instance.getFixtureManager().getFixtureByName(fixtureName);
    }

    private int getFixtureSizeByDragBoardString(String dragBoardString) {
        String[] data = dragBoardString.split(":");
        String fixtureName = data[0];
        PatchFixture patchFixture = StandaloneDMXControlPro.instance.getFixtureManager().getFixtureByName(fixtureName);
        FixtureMode mode;
        if (data.length == 2) {
            String modeName = data[1];
            mode = patchFixture.getModeByName(modeName);
        } else mode = patchFixture.getModes().get(0); //In case there is only one
        return mode.getFixtureChannels().size();
    }

    //SOOOO FUCKING GREAT!!!!
    private Pane getPaneAtXY(double x, double y) {
        return (Pane) grid.getChildren().get(getPaneAtXYPos(x, y));
    }

    private int getPaneAtXYPos(double x, double y) {
        double width = ((Pane) grid.getChildren().get(1)).getWidth();
        double height = ((Pane) grid.getChildren().get(1)).getHeight();
        int betterX = (int) (x / width);
        int betterY = (int) (y / height);
        int pos = betterX + betterY * 32;
        if (pos > 512) pos = 512;
        return pos;
    }
}
