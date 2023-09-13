package de.standaloendmx.standalonedmxcontrolpro.fixture.deserializer;

import com.google.gson.*;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureBulb;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureDimension;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureLens;
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
        if(jsonObject.getAsJsonObject("powerConnectors") != null){
            for (String connectors : jsonObject.getAsJsonObject("powerConnectors").keySet()) {
                powerConnectors.add(new FixturePowerConnector(connectors, jsonObject.getAsJsonObject("powerConnectors").get(connectors).toString()));
            }
        }

        FixtureLens lens = null;
        if(jsonObject.getAsJsonObject("lens") != null) {
            String lensDegrees = "";
            if(jsonObject.getAsJsonObject("lens").getAsJsonArray("degreesMinMax") != null)
                lensDegrees = jsonObject.getAsJsonObject("lens").getAsJsonArray("degreesMinMax").toString();

            String lensName = "";
            if(jsonObject.getAsJsonObject("lens").get("name") != null)
                lensName = jsonObject.getAsJsonObject("lens").get("name").getAsString();
            lens = new FixtureLens(lensName, lensDegrees);
        }


        FixtureBulb bulb = null;
        int lumen = -1;
        int colorTemp = -1;
        if(jsonObject.getAsJsonObject("bulb") != null){
            if(jsonObject.getAsJsonObject("bulb").get("colorTemperature") != null) colorTemp = jsonObject.getAsJsonObject("bulb").get("colorTemperature").getAsInt();
            if(jsonObject.getAsJsonObject("bulb").get("lumens") != null) lumen = jsonObject.getAsJsonObject("bulb").get("lumens").getAsInt();

            bulb = new FixtureBulb(jsonObject.getAsJsonObject("bulb").get("type").getAsString(), colorTemp, lumen);
        }



        FixtureDimension dim = null;
        if(data != null) dim = new FixtureDimension(data.get(0).getAsDouble(), data.get(1).getAsDouble(), data.get(2).getAsDouble());

        double weight = 0;
        if(jsonObject.get("weight") != null) weight = jsonObject.get("weight").getAsDouble();

        int power = 0;
        if(jsonObject.get("power") != null) weight = jsonObject.get("power").getAsInt();

        String dmxConnector = "";
        if(jsonObject.get("DMXconnector") != null) dmxConnector = jsonObject.get("DMXconnector").getAsString();

        FixturePhysical ret = new FixturePhysical(dim,
                weight,
                power,
                dmxConnector,
                powerConnectors,
                bulb,
                lens);
        return ret;
    }
}
