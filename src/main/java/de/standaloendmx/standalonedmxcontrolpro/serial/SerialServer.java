package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.EventRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events.StringListener;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketDecoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketEncoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.SimplePacketRegistry;

import java.util.ArrayList;
import java.util.List;

public class SerialServer {

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

        startScanner();
    }

    public boolean startScanner() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if(ports.length == 0) return false;

        for (SerialPort port : ports) {
            if(!port.getSystemPortName().equals("COM1")){
                if(port.openPort()){
                    System.out.println("Opened serial port "+port.getSystemPortName());
                    //config port
                    port.setBaudRate(9600);
                    port.setNumDataBits(8);
                    port.setNumStopBits(1);
                    port.setParity(SerialPort.NO_PARITY);
                    SerialPortInboundHandler handler = new SerialPortInboundHandler(eventRegistry, port);
                    currentConnections.add(handler);
                    handler.start();

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
