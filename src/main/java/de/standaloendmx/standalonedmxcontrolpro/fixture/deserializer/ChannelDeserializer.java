package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureChannel;
import de.standaloendmx.standalonedmxcontrolpro.fixture.capability.CapabilityType;
import de.standaloendmx.standalonedmxcontrolpro.fixture.capability.ChannelCapability;
import de.standaloendmx.standalonedmxcontrolpro.fixture.future.capability.DMXRange;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChannelDeserializer implements JsonDeserializer<List<FixtureChannel>> {

    @Override
    public List<FixtureChannel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<FixtureChannel> list = new ArrayList<>();
        List<ChannelCapability> capabilities;

        JsonObject obj = json.getAsJsonObject();
        for (String s : json.getAsJsonObject().keySet()) { //going thru channels
            capabilities = new ArrayList<>();
            if (obj.getAsJsonObject(s).get("capabilities") != null) {

                for (JsonElement jsonElement : obj.getAsJsonObject(s).getAsJsonArray("capabilities")) { //all capabilities
                    JsonObject d = jsonElement.getAsJsonObject();
                    CapabilityType type = CapabilityType.getByName(jsonElement.getAsJsonObject().get("type").getAsString());

                    setValues(capabilities, d, type);
                }
            } else if (obj.getAsJsonObject(s).get("capability") != null) {

                JsonObject k = obj.getAsJsonObject(s).get("capability").getAsJsonObject();

                CapabilityType type = CapabilityType.getByName(k.get("type").getAsString());

                setValues(capabilities, k, type);
            }

            int defaultValue = 0;
            //Gets as % or absolute Value...
            if (obj.getAsJsonObject(s).get("defaultValue") != null) {
                if (obj.getAsJsonObject(s).get("defaultValue").getAsString().contains("%"))
                    defaultValue = (Integer.parseInt(obj.getAsJsonObject(s).get("defaultValue").getAsString().replace("%", "")) / 100) * 255;
                else defaultValue = obj.getAsJsonObject(s).get("defaultValue").getAsInt();

            }
            list.add(new FixtureChannel(s, defaultValue, capabilities));
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
