package de.standaloendmx.standalonedmxcontrolpro.serial.network.handler;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;

import java.util.List;

public class PacketDecoder {

    private final IPacketRegistry packetRegistry;

    public PacketDecoder(IPacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    protected void decode(SerialPort channelHandlerContext, CustomByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.readInt(); //TODO replace with setReaderIndex...
        int packetId = byteBuf.readInt();
        if (!packetRegistry.containsPacketId(packetId)) {
            throw new Exception("Received invalid packet id");
        }
        long sessionId = byteBuf.readLong();
        int systemId = byteBuf.readInt();
        Packet packet = packetRegistry.constructPacket(packetId);
        packet.read(byteBuf.readBytes(byteBuf.readableBytes()));

        list.add(packet);
    }

}