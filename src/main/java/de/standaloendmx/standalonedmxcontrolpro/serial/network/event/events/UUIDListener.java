package de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events;

import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.PacketSubscriber;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;

public class UUIDListener {

    @PacketSubscriber
    public void onRespondingUUID(UUIDPacket packet, MySerialPort serialPort) {
        serialPort.setUuid(packet.getUuid());
    }

}
