package de.standaloendmx.standalonedmxcontrolpro.serial;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;

public class TestPacket extends Packet {

    private String testString;

    @Override
    public void read(CustomByteBuf buffer) {
        testString = buffer.readString();
        System.out.println(testString);
    }

    @Override
    public void write(CustomByteBuf buffer) {
        buffer.writeString("Hello !");
    }

    public String getTestString() {
        return testString;
    }
}
