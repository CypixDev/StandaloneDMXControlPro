package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatchDirectoryViewController implements Initializable {

    @FXML
    public static VBox mainBox;

    @FXML
    private Button btnFolder;
    @FXML
    private Button btnReset;
    @FXML
    private TextField tfSearch;

    @FXML
    private TreeView<String> directory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory.setFocusTraversable(false);
        directory.setShowRoot(false);

        TreeItem<String> item = new TreeItem<>("root");
        directory.setRoot(item);

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



        directory.setOnDragDetected(event -> {
            Dragboard dragboard = directory.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, "cameo:5");

            dragboard.setContent(content);
            event.consume();
        });


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
        TreeItem<String> root = directory.getRoot();
        filterTreeItem(root, filterText);
    }

    private void filterTreeItem(TreeItem<String> item, String filterText) {
        boolean hasMatchingChildren = false;

        for (TreeItem<String> child : item.getChildren()) {
            filterTreeItem(child, filterText);
            if (child.isExpanded()) {
                hasMatchingChildren = true;
            }
        }

        boolean matches = item.getValue().toLowerCase().contains(filterText.toLowerCase());

        if (matches || hasMatchingChildren) {
            item.setExpanded(true);
            //item.setValue(item.getValue()); // Aktualisieren Sie die Anzeige, um die Änderung zu übernehmen
        } else {
            item.setExpanded(false);
        }
    }

    private void populatedTreeView(File folder, TreeItem<String> parentItem) {
        if (folder.isDirectory()) {
            TreeItem<String> folderItem = new TreeItem<>(folder.getName());
            parentItem.getChildren().add(folderItem);

            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    populateTreeView(file, folderItem);
                }
            }
        } else {
            TreeItem<String> fileItem = new TreeItem<>(folder.getName());
            parentItem.getChildren().add(fileItem);
        }
    }

    private void populateTreeView(File folder, TreeItem<String> parentItem) {
        if(folder.isDirectory()){
            TreeItem<String> folderItem = new TreeItem<>(folder.getName());
            if(folder.getName().equals("Fixture_Library")) {
                folderItem = parentItem;
            }else{
                parentItem.getChildren().add(folderItem);
            }

            for (File file : folder.listFiles()) {
                populatedTreeView(file, folderItem);
            }

        }else {
            TreeItem<String> fileItem = new TreeItem<>(folder.getName());
            parentItem.getChildren().add(fileItem);
        }
    }
}
