package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.EventRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events.StringListener;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketDecoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketEncoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.SimplePacketRegistry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SerialServer {

    private final Logger logger = LogManager.getLogger(SerialServer.class);

    private static SerialServer instance;

    private final List<SerialPortInboundHandler> currentConnections;
    private final EventRegistry eventRegistry;
    private final IPacketRegistry packetRegistry;

    private final PacketEncoder packetEncoder;
    private final PacketDecoder packetDecoder;

    public SerialServer() {
        instance = this;

        packetRegistry = new SimplePacketRegistry();
        currentConnections = new ArrayList<>();
        eventRegistry = new EventRegistry();
        packetDecoder = new PacketDecoder(packetRegistry);
        packetEncoder = new PacketEncoder(packetRegistry);

        registerEvents();

    }

    public boolean startScanner() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if(ports.length == 0) return false;

        for (SerialPort port : ports) {
                if(port.openPort()){
                    if(port.getDescriptivePortName().startsWith("USB-SERIAL CH340")){
                        System.out.println("Connected to Arduino nano ("+port.getSystemPortName()+")");
                        //config port
                        port.setBaudRate(9600);
                        port.setNumDataBits(8);
                        port.setNumStopBits(1);
                        port.setParity(SerialPort.NO_PARITY);


                        SerialPortInboundHandler handler = new SerialPortInboundHandler(eventRegistry, port);
                        currentConnections.add(handler);
                        handler.start();
                        try {
                            writeAndFlushPacket(port, new UUIDPacket());
                            System.out.println("Sending packet....");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        }
        return true;
    }


    public boolean writeAndFlushPacket(SerialPort serialPort, Packet packet) throws Exception {
        CustomByteBuf buf = new CustomByteBuf(4);
        buf.printInConsole();
        packetEncoder.encode(serialPort, packet, buf);


        buf.printInConsole();
        serialPort.writeBytes(buf.array(), buf.array().length);

        return true;
    }

    public void registerEvents(){
        eventRegistry.registerEvents(new StringListener());
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public List<SerialPortInboundHandler> getCurrentConnections() {
        return currentConnections;
    }

    public IPacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public PacketEncoder getPacketEncoder() {
        return packetEncoder;
    }

    public PacketDecoder getPacketDecoder() {
        return packetDecoder;
    }

    public static SerialServer getInstance() {
        return instance;
    }
}
