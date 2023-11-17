package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DebugPacket extends Packet {

    private final Logger logger = LogManager.getLogger(DebugPacket.class);

    @Override
    public void read(CustomByteBuf buffer) {
        String debugMessage = buffer.readString();

        System.out.println("Debug: "+debugMessage);
    }

    @Override
    public void write(CustomByteBuf buffer) {

    }
}
