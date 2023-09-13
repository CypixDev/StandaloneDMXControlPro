package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureBulb;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureDimension;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixturePhysical;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhysicalDeserializer implements JsonDeserializer<FixturePhysical> {

    @Override
    public FixturePhysical deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray data = jsonObject.getAsJsonArray("dimensions");

        List<FixturePowerConnector> powerConnectors = new ArrayList<>();
        for (String connectors : jsonObject.getAsJsonObject("powerConnectors").keySet()) {
            powerConnectors.add(new FixturePowerConnector(connectors, jsonObject.getAsJsonObject("powerConnectors").get(connectors).toString()));
        }

        String lens = null;
        if(jsonObject.getAsJsonObject("lens") != null) lens = jsonObject.getAsJsonObject("lens").getAsJsonArray("degreesMinMax").toString();


        int lumen = -1;
        int colorTemp = -1;
        if(jsonObject.getAsJsonObject("bulb").get("colorTemperature") != null) colorTemp = jsonObject.getAsJsonObject("bulb").get("colorTemperature").getAsInt();
        if(jsonObject.getAsJsonObject("bulb").get("lumens") != null) lumen = jsonObject.getAsJsonObject("bulb").get("lumens").getAsInt();

        FixtureBulb bulb = new FixtureBulb(jsonObject.getAsJsonObject("bulb").get("type").getAsString(),
                colorTemp, lumen);



        FixtureDimension dim = new FixtureDimension(data.get(0).getAsDouble(), data.get(1).getAsDouble(), data.get(2).getAsDouble());
        FixturePhysical ret = new FixturePhysical(dim,
                jsonObject.get("weight").getAsDouble(),
                jsonObject.get("power").getAsInt(),
                jsonObject.get("DMXconnector").getAsString(),
                powerConnectors,
                bulb,
                lens);
        return ret;
    }
}
