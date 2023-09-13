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
            if(obj.getAsJsonObject(s).get("capabilities") != null){

                for (JsonElement jsonElement : obj.getAsJsonObject(s).getAsJsonArray("capabilities")) { //all capabilities
                    JsonObject d = jsonElement.getAsJsonObject();
                    CapabilityType type = CapabilityType.getByName(jsonElement.getAsJsonObject().get("type").getAsString());

                    setValues(capabilities, d, type);
                }
            }else if(obj.getAsJsonObject(s).get("capability") != null){

                JsonObject k = obj.getAsJsonObject(s).get("capability").getAsJsonObject();

                CapabilityType type = CapabilityType.getByName(k.get("type").getAsString());

                setValues(capabilities, k, type);
            }

            int defaultValue = 0;
            if(obj.getAsJsonObject(s).get("defaultValue") != null) defaultValue = obj.getAsJsonObject(s).get("defaultValue").getAsInt();
            list.add(new FixtureChannel(s, defaultValue, capabilities));
        }



        return list;
    }

    private void setValues(List<ChannelCapability> capabilities, JsonObject d, CapabilityType type) {
        String effectName = type.getName();
        DMXRange dmxRange = new DMXRange(0, 255);
        if(d.get("effectName") != null) effectName = d.get("effectName").getAsString();
        if(d.get("dmxRange") != null) dmxRange = new DMXRange(d.getAsJsonArray("dmxRange").get(0).getAsInt(), d.getAsJsonArray("dmxRange").get(1).getAsInt());

        capabilities.add(new ChannelCapability(type, effectName, dmxRange));
    }
}
