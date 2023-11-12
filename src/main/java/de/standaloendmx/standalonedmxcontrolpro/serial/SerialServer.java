package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;

import java.util.ArrayList;
import java.util.List;

public class SerialServer {

    private final List<SerialConnection> currentConnections;

    public SerialServer() {
        currentConnections = new ArrayList<>();
    }


    public boolean writeAndFlushPacket(SerialPort serialPort, Packet packet){
        CustomByteBuf buf = new CustomByteBuf(); //TODO
        packet.write(buf);
        serialPort.writeBytes(buf.array(), buf.array().length);

        return true;
    }


}
