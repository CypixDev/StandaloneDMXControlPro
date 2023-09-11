package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatchDirectoryViewController implements Initializable {

    @FXML
    public static VBox mainBox;

    @FXML
    private Button btnFolder;

    @FXML
    private TreeView<PatchFixture> directory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory.setFocusTraversable(false);

        TreeItem<PatchFixture> item = new TreeItem<>(new PatchFixture("penis"));
        directory.setRoot(item);

        btnFolder.setOnAction(e -> {
            try {
                Runtime.getRuntime().exec("explorer.exe /open," + "C:\\Users\\pikon\\OneDrive\\Dokumente\\SDCP");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        directory.setOnDragEntered(e -> {
            System.out.println(e.getEventType().getName());
        });

        directory.setOnDragDone(e -> {
            e.acceptTransferModes(TransferMode.COPY);
            System.out.println(e.getEventType().getName());
        });

        directory.setOnDragExited(e -> {
            e.acceptTransferModes(TransferMode.COPY);
            System.out.println(e.getEventType().getName());
        });

        directory.setOnDragDetected(e -> {
            System.out.println(e.getEventType().getName());
        });
        directory.setOnDragDetected(event -> {
            Dragboard dragboard = directory.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, "cameo:5");

            dragboard.setContent(content);
            event.consume();
        });
    }
}
