package de.standaloendmx.standalonedmxcontrolpro.serial;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;

public class TestPacket extends Packet {
    @Override
    public void read(CustomByteBuf buffer) {

    }

    @Override
    public void write(CustomByteBuf buffer) {
        buffer.writeString("Hello !");
    }
}
