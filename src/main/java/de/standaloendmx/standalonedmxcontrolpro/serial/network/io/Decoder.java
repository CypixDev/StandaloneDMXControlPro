package de.standaloendmx.standalonedmxcontrolpro.serial.network.io;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;

public interface Decoder {

    void read(CustomByteBuf buffer);

}
