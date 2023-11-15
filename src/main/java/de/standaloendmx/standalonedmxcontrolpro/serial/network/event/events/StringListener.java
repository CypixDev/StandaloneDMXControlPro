package de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.TestPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.PacketSubscriber;

public class StringListener {


    @PacketSubscriber
    public void onRespondingString(TestPacket packet, SerialPort serialPort){
        System.out.println("Event stuff: "+packet.getTestString());
    }

}
