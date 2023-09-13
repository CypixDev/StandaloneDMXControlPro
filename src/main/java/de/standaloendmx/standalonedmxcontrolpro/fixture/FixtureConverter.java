package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.files.FilesManager;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.PhysicalDeserializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FixtureConverter {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {


        //FileReader file = new FileReader("C:\\Users\\pikon\\Desktop\\stage-wash-7x10w-led-moving-head.json");
        //FileReader file = new FileReader("C:\\Users\\pikon\\Desktop\\mh-x60.json");
        //FileReader file = new FileReader("C:\\Users\\pikon\\Desktop\\zq-b20-mini-spider-light.json");
        //System.out.println(getFixtureFromJson(JsonParser.parseReader(file)).toString());

        long start = System.currentTimeMillis();

        FilesManager filesManager = new FilesManager();
        processFolder(filesManager.getFixtureLibraryFolder());
        //getFixtureFromJson(JsonParser.parseReader(file));

        System.out.println("Took: "+(System.currentTimeMillis()-start)+"ms");

    }
    public static void processFolder(File folder) throws FileNotFoundException {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Wenn es sich um ein Unterverzeichnis handelt, durchsuchen Sie es rekursiv
                        processFolder(file);
                    } else if (file.isFile() && file.getName().toLowerCase().endsWith(".json")) {
                        FileReader reader = new FileReader(file);
                        System.out.println("Processing: "+file.getName());

                        System.out.println(getFixtureFromJson(JsonParser.parseReader(reader)));
                        System.out.println("---------------------");
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
