package de.standaloendmx.standalonedmxcontrolpro.gui.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import de.standaloendmx.standalonedmxcontrolpro.files.FileUtils;
import de.standaloendmx.standalonedmxcontrolpro.files.PatchFixtureAdapter;
import de.standaloendmx.standalonedmxcontrolpro.fixture.Fixture;
import de.standaloendmx.standalonedmxcontrolpro.gui.patch.PatchGridViewController;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
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

        save.setOnAction(e -> {
            PatchManager patchManager = StandaloneDMXControlPro.instance.getPatchManager();

            GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
            gson.registerTypeAdapter(Fixture.class, new PatchFixtureAdapter());

            FileUtils.writeStringToFile(
                    new File(StandaloneDMXControlPro.instance.getFilesManager().getSavesFolder().getAbsolutePath()+"\\"+"latest.json"),
                    gson.create().toJson(patchManager.getPatches()));
        });

        saveAs.setOnAction(e -> {


        });

        open.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Datei auswählen");
            File selectedFile = chooser.showOpenDialog(MainApplication.mainStage);

            //String json = FileUtils.readStringFromFile(new File(StandaloneDMXControlPro.instance.getFilesManager().getSavesFolder().getAbsolutePath()+"\\"+"latest.json"));

            String json = FileUtils.readStringFromFile(selectedFile);

            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(Fixture.class, new PatchFixtureAdapter());

            Type listType = new TypeToken<List<PatchFixture>>(){}.getType();
            List<PatchFixture> list = gson.create().fromJson(json, listType);
            for (PatchFixture patchFixture : list) {
                StandaloneDMXControlPro.instance.getPatchManager().getPatches().add(patchFixture);
            }

            PatchGridViewController.instances.forEach(PatchGridViewController::updatePatch);
        });
    }
}
