package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import de.standaloendmx.standalonedmxcontrolpro.fixture.Fixture;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureChannel;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureMode;
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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private TreeView<Fixture> directory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory.setFocusTraversable(false);
        directory.setShowRoot(false);

        TreeItem<Fixture> rootItem = new TreeItem<>(new Fixture("Root"));
        directory.setRoot(rootItem);

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
                    content.put(DataFormat.PLAIN_TEXT,  item);
                }


                dragboard.setContent(content);
                event.consume();
            }
        });

        directory.setCellFactory(param -> new TreeCell<Fixture>() {
            @Override
            protected void updateItem(Fixture fixture, boolean empty) {
                super.updateItem(fixture, empty);
                if (fixture == null || empty) {
                    setGraphic(null);
                } else {
                    //TODO set icon
                    setText(fixture.getName());
                }
            }
        });

/*        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Löschen");
        contextMenu.getItems().add(deleteMenuItem);*/

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

    public void updateDirectoryFromFiles(){
        File lib = StandaloneDMXControlPro.instance.getFilesManager().getFixtureLibraryFolder();
        directory.getRoot().getChildren().clear();
        populateTreeView(lib, directory.getRoot());
    }
    public void updateDirectory(){
        //Clear directory
        //TODO: reload fixture manager as well
        directory.getRoot().getChildren().clear();


        TreeItem<Fixture> rootItem = directory.getRoot();
        Map<String, List<Fixture>> map = StandaloneDMXControlPro.instance.getFixtureManager().getFixturesPerManufacture();

        for (String s : map.keySet()) {
            TreeItem<Fixture> treeRoot = new TreeItem<>(new Fixture(s));
            for (Fixture fixture : map.get(s)) {
                if(fixture.getModes().size() > 1){
                    TreeItem<Fixture> treeRoot2 = new TreeItem<>(fixture);
                    for (FixtureMode mode : fixture.getModes()) {
                        treeRoot2.getChildren().add(new TreeItem<>(new Fixture(mode.getNameWithChannel())));
                    }
                    treeRoot.getChildren().add(treeRoot2);
                }else {
                    treeRoot.getChildren().add(new TreeItem<>(fixture));
                }
            }
            rootItem.getChildren().add(treeRoot);
        }

    }



    private void filterTreeView(String filterText) {
        TreeItem<Fixture> root = directory.getRoot();
        filterTreeItem(root, filterText);
    }

    private void filterTreeItem(TreeItem<Fixture> item, String filterText) {
        boolean hasMatchingChildren = false;

        for (TreeItem<Fixture> child : item.getChildren()) {
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

    private void populatedTreeView(File folder, TreeItem<Fixture> parentItem) {
        if (folder.isDirectory()) {
            TreeItem<Fixture> folderItem = new TreeItem<>(new Fixture(folder.getName()));
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

            TreeItem<Fixture> fileItem = new TreeItem<>(new Fixture(folder.getName()), view);

            parentItem.getChildren().add(fileItem);
        }
    }

    private void populateTreeView(File folder, TreeItem<Fixture> parentItem) {
        if(folder.isDirectory()){
            TreeItem<Fixture> folderItem = new TreeItem<>(new Fixture(folder.getName()));
            if(folder.getName().equals("Fixture_Library")) {
                folderItem = parentItem;
            }else{
                parentItem.getChildren().add(folderItem);
            }

            for (File file : folder.listFiles()) {
                populatedTreeView(file, folderItem);
            }

        }else {
            TreeItem<Fixture> fileItem = new TreeItem<>(new Fixture(folder.getName()));
            parentItem.getChildren().add(fileItem);
        }
    }
}