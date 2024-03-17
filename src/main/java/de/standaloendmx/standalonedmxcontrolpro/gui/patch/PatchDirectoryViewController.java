package de.standaloendmx.standalonedmxcontrolpro.gui.patch;

import de.standaloendmx.standalonedmxcontrolpro.files.FileUtils;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureMode;
import de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatchDirectoryViewController implements Initializable {

    private static final Logger logger = LogManager.getLogger(PatchDirectoryViewController.class);
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

    /**
     * Converts an InputStream to a String.
     *
     * @param inputStream The InputStream to convert.
     * @return The converted String.
     */

    @Deprecated
    public static String convertInputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator()); // Um Zeilenumbrüche beizubehalten
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        directory.setFocusTraversable(false);
        directory.setShowRoot(false);

        TreeItem<PatchFixture> rootItem = new TreeItem<>(new PatchFixture("Root"));
        directory.setRoot(rootItem);

        btnBulb.setOnAction(e -> {
            Desktop desk = Desktop.getDesktop();
            try {
                desk.browse(new URI("https://open-fixture-library.org/"));
            } catch (IOException | URISyntaxException ex) {
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

        btnReset.setOnAction(e -> StandaloneDMXControlPro.instance.getFilesManager().resetFixtureLibrary());

        btnReload.setOnAction(e -> {
            updateDirectory();
            directory.refresh();
        });

        directory.setOnMouseClicked(e -> {
            //For double-click on item
            //if(e.getClickCount() == 2) System.out.println("opEN!");
        });

        directory.setOnDragDetected(event -> {
            TreeItem<PatchFixture> selectedItem = directory.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.isLeaf()) { //So that folders cannot be moved
                Dragboard dragboard = directory.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();

                if (selectedItem.getValue().isMode()) {
                    PatchFixture item = selectedItem.getParent().getValue();
                    String modeName = selectedItem.getValue().getName();
                    content.put(DataFormat.PLAIN_TEXT, item.getName() + ":" + modeName);

                } else {
                    PatchFixture item = selectedItem.getValue();
                    content.put(DataFormat.PLAIN_TEXT, item.getName());
                }


                dragboard.setContent(content);
                event.consume();
            }
        });

        directory.setCellFactory(param -> new TreeCell<>() {
            @Override
            protected void updateItem(PatchFixture patchFixture, boolean empty) {
                super.updateItem(patchFixture, empty);
                if (patchFixture == null || empty) {
                    setGraphic(null);
                } else {
                    setText(patchFixture.getName());
                    if (!patchFixture.isMode()) {
                        String svg = FileUtils.getSVGPath("/gui/img/icons/fixture/" + patchFixture.getCategories().get(0).getFileName() + ".svg");

                        SVGPath svgPath = new SVGPath();
                        svgPath.setContent(svg);

                        svgPath.setFill(Color.BLACK);
                        svgPath.setScaleX(0.7);
                        svgPath.setScaleY(0.7);

                        try {
                            setGraphic(svgPath);
                        } catch (Exception e) {
                            logger.error("Error while setting tree-item graphic", e);
                        }
                    }
                }
            }
        });


        directory.setOnContextMenuRequested(event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem openMenuItem = new MenuItem("Öffnen");
            contextMenu.getItems().add(openMenuItem);

            TreeItem<PatchFixture> selectedItem = directory.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (!selectedItem.getValue().isMode()) {
                    contextMenu.show(directory, event.getScreenX(), event.getScreenY());
                }

                openMenuItem.setOnAction(actionEvent -> System.out.println("Open fixture info..."));
            }
        });

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> filterTreeView(newValue));

        updateDirectory();
    }

    /**
     * Updates the directory view by populating it with files from the fixture library folder.
     */

    @Deprecated
    public void updateDirectoryFromFiles() {
        File lib = StandaloneDMXControlPro.instance.getFilesManager().getFixtureLibraryFolder();
        directory.getRoot().getChildren().clear();
        populateTreeView(lib, directory.getRoot());
    }

    public void updateDirectory() {
        //Clear directory
        //TODO: reload fixture manager as well //remove ?
        directory.getRoot().getChildren().clear();


        TreeItem<PatchFixture> rootItem = directory.getRoot();
        Map<String, List<PatchFixture>> map = StandaloneDMXControlPro.instance.getFixtureManager().getFixturesPerManufacture();

        for (String s : map.keySet()) {
            TreeItem<PatchFixture> treeRoot = new TreeItem<>(new PatchFixture(s));
            for (PatchFixture patchFixture : map.get(s)) {
                if (patchFixture.getModes().size() > 1) {
                    TreeItem<PatchFixture> treeRoot2 = new TreeItem<>(patchFixture);
                    for (FixtureMode mode : patchFixture.getModes()) {
                        treeRoot2.getChildren().add(new TreeItem<>(new PatchFixture(mode.getNameWithChannel())));
                    }
                    treeRoot.getChildren().add(treeRoot2);
                } else {
                    treeRoot.getChildren().add(new TreeItem<>(patchFixture));
                }
            }
            rootItem.getChildren().add(treeRoot);
        }

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
                    Objects.requireNonNull(getClass().getResourceAsStream("/gui/img/logo/logo_mini_transparent.png")));
            ImageView view = new ImageView(nodeImage);
            view.setFitHeight(16);
            view.setFitWidth(16);

            TreeItem<PatchFixture> fileItem = new TreeItem<>(new PatchFixture(folder.getName()), view);

            parentItem.getChildren().add(fileItem);
        }
    }

    private void populateTreeView(File folder, TreeItem<PatchFixture> parentItem) {
        if (folder.isDirectory()) {
            TreeItem<PatchFixture> folderItem = new TreeItem<>(new PatchFixture(folder.getName()));
            if (folder.getName().equals("Fixture_Library")) {
                folderItem = parentItem;
            } else {
                parentItem.getChildren().add(folderItem);
            }

            for (File file : Objects.requireNonNull(folder.listFiles())) {
                populatedTreeView(file, folderItem);
            }

        } else {
            TreeItem<PatchFixture> fileItem = new TreeItem<>(new PatchFixture(folder.getName()));
            parentItem.getChildren().add(fileItem);
        }
    }
}
