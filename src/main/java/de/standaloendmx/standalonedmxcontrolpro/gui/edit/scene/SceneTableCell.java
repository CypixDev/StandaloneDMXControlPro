package de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene;


import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class SceneTableCell extends TableCell<MyScene, Void> {
    private final HBox container = new HBox();
    private final Label label = new Label();
    private final Button button1 = new Button("Button 1");
    private final Button button2 = new Button("Button 2");

    public SceneTableCell() {
        container.getChildren().addAll(label, button1, button2);
        button1.setOnAction(event -> {
            System.out.println("Lol2");
            // Handle the button click event
            // You can access the item associated with this cell using getItem()
        });
        button2.setOnAction(event -> {
            System.out.println("lol");
            // Handle the button click event
            // You can access the item associated with this cell using getItem()
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            // Access the item associated with this cell using getItem()
            label.setText("getItem().toString()");
            setGraphic(container);
        }
    }

}
