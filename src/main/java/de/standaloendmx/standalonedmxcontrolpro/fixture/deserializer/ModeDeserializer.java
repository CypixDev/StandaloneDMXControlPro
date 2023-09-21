package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureMode;
import de.standaloendmx.standalonedmxcontrolpro.fixture.capability.CapabilityType;
import de.standaloendmx.standalonedmxcontrolpro.fixture.capability.ChannelCapability;
import de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability.DMXRange;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModeDeserializer implements JsonDeserializer<List<FixtureMode>> {

    @Override
    public List<FixtureMode> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<FixtureMode> list = new ArrayList<>();

        JsonArray arr = json.getAsJsonArray();
        for (JsonElement jsonElement : arr) { //Threw all modes
            JsonObject mode = jsonElement.getAsJsonObject();
            String name = mode.get("name").getAsString();
            String shortName = name;
            if (mode.get("shortName") != null) shortName = mode.get("shortName").getAsString();
            List<String> channelList = new ArrayList<>();
            for (JsonElement channels : mode.get("channels").getAsJsonArray()) {
                channelList.add(String.valueOf(channels));
            }
            list.add(new FixtureMode(name, shortName, channelList));
        }


        return list;
    }

    private void setValues(List<ChannelCapability> capabilities, JsonObject jsonObject, CapabilityType type) {
        String effectName = type.getName();
        DMXRange dmxRange = new DMXRange(0, 255);
        if (jsonObject.get("effectName") != null) effectName = jsonObject.get("effectName").getAsString();
        if (jsonObject.get("dmxRange") != null)
            dmxRange = new DMXRange(jsonObject.getAsJsonArray("dmxRange").get(0).getAsInt(), jsonObject.getAsJsonArray("dmxRange").get(1).getAsInt());

        capabilities.add(new ChannelCapability(type, effectName, dmxRange));
    }
}
