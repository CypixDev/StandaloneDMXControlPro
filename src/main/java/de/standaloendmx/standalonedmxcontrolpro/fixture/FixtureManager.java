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
import java.util.*;

public class FixtureManager {

    private Logger logger = LogManager.getLogger(FixtureManager.class);

    private List<Fixture> fixtures;

    public FixtureManager() {
        fixtures = new ArrayList<>();
    }

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public Fixture getFixtureByName(String fixtureName) {
        for (Fixture fixture : fixtures) {
            if(fixture.getName().equals(fixtureName))return fixture;
        }
        return null;
    }

    public SortedMap<String, List<Fixture>> getFixturesPerManufacture(){ //Sorted map is beautiful!

        SortedMap<String, List<Fixture>> map = new TreeMap<>();

        for (Fixture fixture : fixtures) {
            if(map.containsKey(fixture.getManufacture())){
                List<Fixture> tmp = map.get(fixture.getManufacture());
                tmp.add(fixture);
                map.put(fixture.getManufacture(), tmp);
            }else{
                List<Fixture> list = new ArrayList<>();
                list.add(fixture);
                map.put(fixture.getManufacture(), list);
            }
        }


        return map;
    }

    public void loadAllFixturesFromFiles(){
        File lib = StandaloneDMXControlPro.instance.getFilesManager().getFixtureLibraryFolder();
        logger.info("Starting loading fixtures from file.");
        long start = System.currentTimeMillis();
        try {
            processFolder(lib);
        } catch (Exception e) {
            logger.error("Failed loading fixtures", e);
        }
        logger.info("Finished loading, took "+(System.currentTimeMillis()-start)+" ms");
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
                        logger.debug("Loading fixture: "+file.getName());
                        Fixture fixture = getFixtureFromJson(JsonParser.parseReader(reader));
                        fixture.setManufacture(folder.getName());
                        fixtures.add(fixture);
                    }
                }
            }
        }
    }


    public static Fixture getFixtureFromJson(JsonElement jsonElement){
        Gson gson = new GsonBuilder().registerTypeAdapter(FixtureDimension.class, new PhysicalDeserializer()).create();

        return gson.fromJson(jsonElement, Fixture.class);
    }
}
