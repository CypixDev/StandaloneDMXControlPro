package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.fixture.enums.FixtureCategories;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDeserializer implements JsonDeserializer<List<FixtureCategories>> {
    @Override
    public List<FixtureCategories> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray dataArray = json.getAsJsonArray();
        List<FixtureCategories> list = new ArrayList<>();
        for (JsonElement jsonElement : dataArray) {
            list.add(FixtureCategories.getByName(jsonElement.getAsString()));
        }


        return list;
    }
}
