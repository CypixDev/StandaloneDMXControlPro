package de.standaloendmx.standalonedmxcontrolpro.serial.network.handler;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.SerialServer;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.EventRegistry;

import java.nio.ByteBuffer;

public class SerialPortInboundHandler extends Thread{

    private final EventRegistry eventRegistry;
    private final SerialPort serialPort;

    public SerialPortInboundHandler(EventRegistry eventRegistry, SerialPort serialPort) {
        this.eventRegistry = eventRegistry;
        this.serialPort = serialPort;
    }

    @Override
    public void run() {
        try {
            startWaitingForPackets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startWaitingForPackets() throws Exception {
        while(serialPort.isOpen()){
            if(serialPort.bytesAvailable() >= 4){
                byte[] buffer = new byte[4];
                serialPort.readBytes(buffer, 4);

                int packetSize = ByteBuffer.wrap(buffer).getInt();
                System.out.println("Packet size: "+packetSize);
                buffer = new byte[packetSize-4]; //-4 because first int already read
                serialPort.readBytes(buffer, packetSize);

                for (byte b : buffer) {
                    System.out.print(" "+b);
                }
                System.out.println();

                Packet packet = SerialServer.getInstance().getPacketDecoder().decode(serialPort, new CustomByteBuf(buffer));
                channelRead(serialPort, packet);
            }
        }

    }

    protected void channelRead(SerialPort serialPort, Packet packet){
        eventRegistry.invoke(packet, serialPort);
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }
}
