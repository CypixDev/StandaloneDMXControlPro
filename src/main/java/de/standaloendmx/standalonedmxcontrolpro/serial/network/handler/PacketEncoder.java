package de.standaloendmx.standalonedmxcontrolpro.serial.network.handler;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;

public class PacketEncoder {

    private final IPacketRegistry packetRegistry;

    public PacketEncoder(IPacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    protected void encode(SerialPort serialPort, Packet packet, CustomByteBuf byteBuf) throws Exception {
        // Get packet id and write into final packet
        int packetId = packetRegistry.getPacketId(packet.getClass());
        if (packetId < 0) {
            throw new Exception("Returned PacketId by registry is < 0");
        }
        byteBuf.writeInt(packetId);
/*        byteBuf.writeInt(packet.getSystemId());
        byteBuf.writeLong(packet.getSessionId());*/
        packet.write(byteBuf);

    }

}