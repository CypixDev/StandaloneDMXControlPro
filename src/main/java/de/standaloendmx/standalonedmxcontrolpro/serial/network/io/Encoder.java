package de.standaloendmx.standalonedmxcontrolpro.serial.network.io;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;

public interface Encoder {

    void write(CustomByteBuf buffer);

}
