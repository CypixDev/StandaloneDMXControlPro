package de.standaloendmx.standalonedmxcontrolpro.files;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.standaloendmx.standalonedmxcontrolpro.fixture.Fixture;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;

import java.io.IOException;

public class PatchFixtureAdapter extends TypeAdapter<Fixture> {

    @Override
    public void write(JsonWriter out, Fixture value) throws IOException {
        out.value(value.getFixtureKey());
    }

    @Override
    public Fixture read(JsonReader in) throws IOException {
        return StandaloneDMXControlPro.instance.getFixtureManager().getFixtureByKey(in.nextString());
    }
}
