package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer.PhysicalDeserializer;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class FixtureConverter {

    public static void main(String[] args) throws FileNotFoundException {

        FileReader file = new FileReader("C:\\Users\\pikon\\Desktop\\stage-wash-7x10w-led-moving-head.json");
        //FileReader file = new FileReader("C:\\Users\\pikon\\Desktop\\mh-x60.json");
        //FileReader file = new FileReader("C:\\Users\\pikon\\Desktop\\zq-b20-mini-spider-light.json");
        System.out.println(getFixtureFromJson(JsonParser.parseReader(file)).toString());

    }


    public static Fixture getFixtureFromJson(JsonElement jsonElement){

        Gson gson = new GsonBuilder().registerTypeAdapter(FixtureDimension.class, new PhysicalDeserializer()).create();


        return gson.fromJson(jsonElement, Fixture.class);
    }

}
