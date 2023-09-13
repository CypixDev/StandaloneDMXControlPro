package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.fixture.enums.FixtureLinkType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkDeserializer implements JsonDeserializer<Map<FixtureLinkType, List<String>>> {
    @Override
    public Map<FixtureLinkType, List<String>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Map<FixtureLinkType, List<String>> retMap = new HashMap<>();

        List<String> links;
        for (Object o : obj.keySet().toArray()) {
            links = new ArrayList<>();
            FixtureLinkType type = FixtureLinkType.getByName(o.toString());
            for (JsonElement jsonElement : obj.get(o.toString()).getAsJsonArray()) {
                links.add(jsonElement.getAsString());
            }
            retMap.put(type, links);
        }
        return retMap;
    }
}
