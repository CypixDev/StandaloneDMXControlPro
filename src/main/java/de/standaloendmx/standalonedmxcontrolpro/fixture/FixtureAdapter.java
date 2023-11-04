package de.standaloendmx.standalonedmxcontrolpro.fixture;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class FixtureAdapter implements JsonDeserializer<PatchFixture> {

    @Override
    public PatchFixture deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {

        return null;
    }
}
