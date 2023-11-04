package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.PhysicalDeserializer;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class FixtureManager {

    private Logger logger = LogManager.getLogger(FixtureManager.class);

    private List<PatchFixture> patchFixtures;

    public FixtureManager() {
        patchFixtures = new ArrayList<>();
    }

    public static PatchFixture getFixtureFromJson(JsonElement jsonElement) {
        Gson gson = new GsonBuilder().registerTypeAdapter(FixtureDimension.class, new PhysicalDeserializer()).create();

        return gson.fromJson(jsonElement, PatchFixture.class);
    }

    public List<PatchFixture> getFixtures() {
        return patchFixtures;
    }

    public PatchFixture getFixtureByName(String fixtureName) {
        for (PatchFixture patchFixture : patchFixtures) {
            if (patchFixture.getName().equals(fixtureName)) return patchFixture;
        }
        return null;
    }

    public SortedMap<String, List<PatchFixture>> getFixturesPerManufacture() { //Sorted map is beautiful!

        SortedMap<String, List<PatchFixture>> map = new TreeMap<>();

        for (PatchFixture patchFixture : patchFixtures) {
            if (map.containsKey(patchFixture.getManufacture())) {
                List<PatchFixture> tmp = map.get(patchFixture.getManufacture());
                tmp.add(patchFixture);
                map.put(patchFixture.getManufacture(), tmp);
            } else {
                List<PatchFixture> list = new ArrayList<>();
                list.add(patchFixture);
                map.put(patchFixture.getManufacture(), list);
            }
        }


        return map;
    }

    public void loadAllFixturesFromFiles() {
        File lib = StandaloneDMXControlPro.instance.getFilesManager().getFixtureLibraryFolder();
        logger.info("Starting loading fixtures from file.");
        long start = System.currentTimeMillis();
        try {
            processFolder(lib);
        } catch (Exception e) {
            logger.error("Failed loading fixtures", e);
        }
        logger.info("Finished loading, took " + (System.currentTimeMillis() - start) + " ms");
    }

    private void processFolder(File folder) throws FileNotFoundException {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Wenn es sich um ein Unterverzeichnis handelt, durchsuchen Sie es rekursiv
                        processFolder(file);
                    } else if (file.isFile() && file.getName().toLowerCase().endsWith(".json")) {
                        FileReader reader = new FileReader(file);
                        logger.debug("Loading fixture: " + file.getName());
                        PatchFixture patchFixture = getFixtureFromJson(JsonParser.parseReader(reader));
                        patchFixture.setManufacture(folder.getName());
                        patchFixtures.add(patchFixture);
                    }
                }
            }
        }
    }

    public PatchFixture getFixtureByKey(String key) {
        for (PatchFixture patchFixture : patchFixtures) {
            if (patchFixture.getFixtureKey().equals(key)) return patchFixture;
        }
        return null;
    }
}
