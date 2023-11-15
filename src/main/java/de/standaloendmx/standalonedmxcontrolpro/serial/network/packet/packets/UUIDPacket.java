package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;

public class UUIDPacket extends Packet {
    @Override
    public void read(CustomByteBuf buffer) {
        System.out.println("UUID: "+buffer.readString());
    }

    @Override
    public void write(CustomByteBuf buffer) {

    }
}
