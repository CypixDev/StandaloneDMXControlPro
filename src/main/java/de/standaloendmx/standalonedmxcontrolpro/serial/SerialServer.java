package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.EventRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events.StringListener;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketDecoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketEncoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.SubscribedPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.DebugPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.PingPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.TestPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.SimplePacketRegistry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SerialServer extends Thread{

    private final Logger logger = LogManager.getLogger(SerialServer.class);

    public boolean scannerActive;
    private static SerialServer instance;

    private final List<SerialPortInboundHandler> currentConnections;
    private final EventRegistry eventRegistry;
    private final IPacketRegistry packetRegistry;

    private final PacketEncoder packetEncoder;
    private final PacketDecoder packetDecoder;


    public SerialServer() {
        instance = this;

        scannerActive = true;
        packetRegistry = new SimplePacketRegistry();
        currentConnections = new ArrayList<>();
        eventRegistry = new EventRegistry();
        packetDecoder = new PacketDecoder(packetRegistry);
        packetEncoder = new PacketEncoder(packetRegistry);

        registerEvents();
        startScanner();
    }

    private void startScanner() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                scan();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }


    public boolean scan() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if(ports.length == 0) return false;

        for (SerialPort port : ports) {
                if(port.openPort()){
                    //if(port.getDescriptivePortName().startsWith("USB-SERIAL CH340")){
                    if(port.getDescriptivePortName().startsWith("Arduino Uno")){
                        logger.info("Connected to Arduino nano ("+port.getSystemPortName()+")");
                        //config port
                        port.setBaudRate(9600);
                        port.setNumDataBits(8);
                        port.setNumStopBits(1);
                        port.setParity(SerialPort.NO_PARITY);


                        SerialPortInboundHandler handler = new SerialPortInboundHandler(eventRegistry, port);
                        currentConnections.add(handler);
                        handler.start();
                        try {
                            handler.getSubscribedPackets().add(new SubscribedPacket(UUIDPacket.class));
                            writeAndFlushPacket(port, new UUIDPacket());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        }
        return true;
    }

    public SerialPortInboundHandler startCom(MySerialPort mySerialPort){
        SerialPort port = mySerialPort.getSerialPort();
        port.openPort();

        logger.info("Connected to "+port.getSystemPortName());

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return handler;
    }

    public List<MySerialPort> getAvailableComPorts() {
        List<MySerialPort> ret = new ArrayList<>();
        SerialPort[] ports = SerialPort.getCommPorts();

        for (SerialPort port : ports) {
            if(!port.isOpen()){
                ret.add(new MySerialPort(port));
            }
        }
        return ret;
    }


    public boolean writeAndFlushPacket(SerialPort serialPort, Packet packet) throws Exception {
        CustomByteBuf buf = new CustomByteBuf(8);

        packetEncoder.encode(serialPort, packet, buf);
        serialPort.writeBytes(buf.array(), buf.array().length);
        //System.out.println(Arrays.toString(buf.array()));
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
