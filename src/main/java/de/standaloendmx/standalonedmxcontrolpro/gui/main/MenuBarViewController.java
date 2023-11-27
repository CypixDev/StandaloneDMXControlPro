package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.standaloendmx.standalonedmxcontrolpro.files.FileUtils;
import de.standaloendmx.standalonedmxcontrolpro.files.PatchFixtureAdapter;
import de.standaloendmx.standalonedmxcontrolpro.fixture.PatchFixture;
import de.standaloendmx.standalonedmxcontrolpro.gui.patch.PatchGridViewController;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuBarViewController implements Initializable {

    @FXML
    private MenuItem close;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem saveAs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        save.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        save.setOnAction(e -> {
            PatchManager patchManager = StandaloneDMXControlPro.instance.getPatchManager();

            GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
            gson.registerTypeAdapter(PatchFixture.class, new PatchFixtureAdapter());

            FileUtils.writeStringToFile(
                    new File(StandaloneDMXControlPro.instance.getFilesManager().getSavesFolder().getAbsolutePath() + "\\" + "latest.json"),
                    gson.create().toJson(patchManager.getPatches()));
        });

        saveAs.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Textdateien", "*.json"));

            chooser.setTitle("Speicherort wählen");
            File selectedFile = chooser.showOpenDialog(MainApplication.mainStage);

        });

        open.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Textdateien", "*.json"));
            chooser.setTitle("Datei auswählen");
            File selectedFile = chooser.showOpenDialog(MainApplication.mainStage);

            //String json = FileUtils.readStringFromFile(new File(StandaloneDMXControlPro.instance.getFilesManager().getSavesFolder().getAbsolutePath()+"\\"+"latest.json"));

            String json = FileUtils.readStringFromFile(selectedFile);

            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(PatchFixture.class, new PatchFixtureAdapter());

            Type listType = new TypeToken<List<de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture>>() {
            }.getType();
            List<de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture> list = gson.create().fromJson(json, listType);
            for (de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture patchFixture : list) {
                StandaloneDMXControlPro.instance.getPatchManager().addPatch(patchFixture);
            }

            PatchGridViewController.instances.forEach(PatchGridViewController::updatePatch);
        });
    }
}
