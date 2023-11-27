package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;

public class PingPacket extends Packet {
    @Override
    public void read(CustomByteBuf buffer) {
        long stamp = buffer.readLong();
        System.out.println("Ping: " + (System.currentTimeMillis() - stamp) + "ms");
    }

    @Override
    public void write(CustomByteBuf buffer) {
        buffer.writeLong(System.currentTimeMillis());
        //buffer.writeInt(4);
    }
}
