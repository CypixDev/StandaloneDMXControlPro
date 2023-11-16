package de.standaloendmx.standalonedmxcontrolpro.serial;

import de.standaloendmx.standalonedmxcontrolpro.serial.network.exception.PacketRegistrationException;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.TestPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;

import java.util.Scanner;


public class SerialTest {

    public static void main(String[] args) throws PacketRegistrationException {
        SerialServer server = new SerialServer();

        server.getPacketRegistry().registerPacket(0, new TestPacket());
        server.getPacketRegistry().registerPacket(1, new UUIDPacket());
        server.startScanner();

    }
}
