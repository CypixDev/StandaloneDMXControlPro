package de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.TestPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.PacketSubscriber;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;

import java.util.UUID;

public class StringListener {


    @PacketSubscriber
    public void onRespondingString(UUIDPacket packet, MySerialPort serialPort){
        System.out.println("Got uuid! "+packet.getUuid());
    }

}
