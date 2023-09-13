package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.sound.midi.Patch;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatchDirectoryViewController implements Initializable {

    @FXML
    public static VBox mainBox;

    @FXML
    private Button btnBulb;
    @FXML
    private Button btnFolder;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnReload;
    @FXML
    private TextField tfSearch;

    @FXML
    private TreeView<PatchFixture> directory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory.setFocusTraversable(false);
        directory.setShowRoot(false);

        TreeItem<PatchFixture> item = new TreeItem<>(new PatchFixture("Root"));
        directory.setRoot(item);

        btnBulb.setOnAction(e -> {
            Desktop desk = Desktop.getDesktop();
            try {
                desk.browse(new URI("https://open-fixture-library.org/"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnFolder.setOnAction(e -> {
            try {
                Runtime.getRuntime().exec("explorer.exe /open," + StandaloneDMXControlPro.instance.getFilesManager().getFixtureLibraryFolder().getAbsolutePath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnReset.setOnAction(e -> {
            StandaloneDMXControlPro.instance.getFilesManager().resetFixtureLibrary();
        });

        btnReload.setOnAction(e -> {
            updateDirectory();
            directory.refresh();
        });



        directory.setOnDragDetected(event -> {
            Dragboard dragboard = directory.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, "cameo:5");

            dragboard.setContent(content);
            event.consume();
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Löschen");
        contextMenu.getItems().add(deleteMenuItem);

/* TODO       directory.setOnContextMenuRequested(event -> {
            TreeItem<String> selectedItem = directory.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Fügen Sie hier die Aktion zum Löschen des ausgewählten Elements hinzu
                deleteMenuItem.setOnAction(actionEvent -> {
                    if (selectedItem.getParent() != null) {
                        selectedItem.getParent().getChildren().remove(selectedItem);
                    } else {
                        // Root-Element kann nicht gelöscht werden
                    }
                });
                contextMenu.show(directory, event.getScreenX(), event.getScreenY());
            }
        });*/

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterTreeView(newValue);
            }
        });

        updateDirectory();

    }

    public void updateDirectory(){
        File lib = StandaloneDMXControlPro.instance.getFilesManager().getFixtureLibraryFolder();
        directory.getRoot().getChildren().clear();
        populateTreeView(lib, directory.getRoot());
    }


    private void filterTreeView(String filterText) {
        TreeItem<PatchFixture> root = directory.getRoot();
        filterTreeItem(root, filterText);
    }

    private void filterTreeItem(TreeItem<PatchFixture> item, String filterText) {
        boolean hasMatchingChildren = false;

        for (TreeItem<PatchFixture> child : item.getChildren()) {
            filterTreeItem(child, filterText);
            if (child.isExpanded()) {
                hasMatchingChildren = true;
            }
        }

        boolean matches = item.getValue().getName().toLowerCase().contains(filterText.toLowerCase());

        if (matches || hasMatchingChildren) {
            item.setExpanded(true);
            //item.setValue(item.getValue()); // Aktualisieren Sie die Anzeige, um die Änderung zu übernehmen
        } else {
            item.setExpanded(false);
        }
    }

    private void populatedTreeView(File folder, TreeItem<PatchFixture> parentItem) {
        if (folder.isDirectory()) {
            TreeItem<PatchFixture> folderItem = new TreeItem<>(new PatchFixture(folder.getName()));
            parentItem.getChildren().add(folderItem);

            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    populateTreeView(file, folderItem);
                }
            }
        } else {
            Image nodeImage = new Image(
                    getClass().getResourceAsStream("/gui/img/logo/logo_mini_transparent.png"));
            ImageView view = new ImageView(nodeImage);
            view.setFitHeight(16);
            view.setFitWidth(16);

            TreeItem<PatchFixture> fileItem = new TreeItem<>(new PatchFixture(folder.getName()), view);

            parentItem.getChildren().add(fileItem);
        }
    }

    private void populateTreeView(File folder, TreeItem<PatchFixture> parentItem) {
        if(folder.isDirectory()){
            TreeItem<PatchFixture> folderItem = new TreeItem<>(new PatchFixture(folder.getName()));
            if(folder.getName().equals("Fixture_Library")) {
                folderItem = parentItem;
            }else{
                parentItem.getChildren().add(folderItem);
            }

            for (File file : folder.listFiles()) {
                populatedTreeView(file, folderItem);
            }

        }else {
            TreeItem<PatchFixture> fileItem = new TreeItem<>(new PatchFixture(folder.getName()));
            parentItem.getChildren().add(fileItem);
        }
    }
}
