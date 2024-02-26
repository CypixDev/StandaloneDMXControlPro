package de.standaloendmx.standalonedmxcontrolpro.serial.network.handler;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.SerialServer;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.EventRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.SubscribedPacket;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SerialPortInboundHandler extends Thread {

    private final EventRegistry eventRegistry;
    private final SerialPort serialPort;
    private MySerialPort mySerialPort;

    private List<SubscribedPacket> subscribedPackets = new ArrayList<>();


    private String uuid;

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
        while (serialPort.isOpen()) {
            if (serialPort.bytesAvailable() >= 4) {
                byte[] buffer = new byte[4];
                serialPort.readBytes(buffer, 4);
                System.out.println(Arrays.toString(buffer));

                int packetSize = ByteBuffer.wrap(buffer).getInt();
                System.out.println("Incomming Size: " + packetSize);
                buffer = new byte[packetSize - 4]; //-4 because first int already read

                long timeoutStamp = System.currentTimeMillis();
                boolean timeout = false;
                while (serialPort.bytesAvailable() < buffer.length) {
                    if (System.currentTimeMillis() - timeoutStamp > 900){
                        System.err.println("Reading packet took more than 300 millis to arrive. continue.");
                        timeout = true;
                        serialPort.readBytes(new byte[serialPort.bytesAvailable()+1], serialPort.bytesAvailable());
                        break;
                    }

                        //throw new TimeoutException("Reading packet took more than 300 millis to arrive");
                } //Waiting for all bytes from packet to arrive!
                if(!timeout){
                    serialPort.readBytes(buffer, packetSize);


                    Packet packet = SerialServer.getInstance().getPacketDecoder().decode(serialPort, new CustomByteBuf(buffer));
                    channelRead(mySerialPort, packet);
                }
            }
        }
    }

    protected void channelRead(MySerialPort serialPort, Packet packet) {
        eventRegistry.invoke(packet, serialPort);
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public List<SubscribedPacket> getSubscribedPackets() {
        return subscribedPackets;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setMySerialPort(MySerialPort mySerialPort) {
        this.mySerialPort = mySerialPort;
    }
}
