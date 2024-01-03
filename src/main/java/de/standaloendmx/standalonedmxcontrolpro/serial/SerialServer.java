package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.gui.deploy.DeployViewController;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.EventRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.event.events.UUIDListener;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketDecoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.PacketEncoder;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.SimplePacketRegistry;
import javafx.application.Platform;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SerialServer extends Thread {

    private static SerialServer instance;
    private final Logger logger = LogManager.getLogger(SerialServer.class);
    private final List<MySerialPort> currentConnections;
    private final EventRegistry eventRegistry;
    private final IPacketRegistry packetRegistry;
    private final PacketEncoder packetEncoder;
    private final PacketDecoder packetDecoder;
    public boolean scannerActive;


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

    public static SerialServer getInstance() {
        return instance;
    }

    private void startScanner() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                scan();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (DeployViewController.instance != null)
                            DeployViewController.instance.refreshListAndStatus();
                    }
                });
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    public boolean scan() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) return false;

        for (SerialPort port : ports) {
            if (port.openPort()) {
                //if(port.getDescriptivePortName().startsWith("USB-SERIAL CH340")){
                if (port.getDescriptivePortName().startsWith("Arduino Uno")) {
                    logger.info("Connected to Arduino nano (" + port.getSystemPortName() + ")");

                    configPort(port);
                    try {
                        writeAndFlushPacket(port, new UUIDPacket());
                    } catch (Exception e) {
                        logger.error(e);
                    }
                } else port.closePort();
            }
        }
        return true;
    }

    private SerialPortInboundHandler configPort(SerialPort port) {
        port.setBaudRate(9600);
        port.setNumDataBits(8);
        port.setNumStopBits(1);
        port.setParity(SerialPort.NO_PARITY);


        SerialPortInboundHandler handler = new SerialPortInboundHandler(eventRegistry, port);
        MySerialPort mySerialPort = new MySerialPort(port, handler);
        handler.setMySerialPort(mySerialPort);
        currentConnections.add(mySerialPort);

        handler.start();
        return handler;
    }

    public SerialPortInboundHandler startCom(MySerialPort mySerialPort) {
        SerialPort port = mySerialPort.getSerialPort();
        port.openPort();

        logger.info("Connected to " + port.getSystemPortName());

        //config port
        SerialPortInboundHandler handler = configPort(port);
        try {
            writeAndFlushPacket(port, new UUIDPacket());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return handler;
    }

    public List<MySerialPort> getAvailableComPorts() {
        List<MySerialPort> ret = new ArrayList<>();

        for (MySerialPort currentConnection : SerialServer.getInstance().getCurrentConnections()) {
            if (!currentConnection.isAdded()) ret.add(currentConnection);
        }

        return ret;
    }

    public boolean writeAndFlushPacket(SerialPort serialPort, Packet packet) throws Exception {
        CustomByteBuf buf = new CustomByteBuf(8);

        packetEncoder.encode(serialPort, packet, buf);

        byte[] toSend = buf.array();
        for (int i = 0; i < toSend.length; i+=64) {
            if((i+64) > toSend.length){
                serialPort.writeBytes(toSend, toSend.length-i, i);
            }else{
                serialPort.writeBytes(toSend, 64, i);
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("Sendung heap....");
            }
        }

        serialPort.writeBytes(buf.array(), buf.array().length);
        //System.out.println(Arrays.toString(buf.array()));
        return true;
    }
    public boolean writeAndFlushPacket(MySerialPort serialPort, Packet packet) throws Exception {
        return writeAndFlushPacket(serialPort.getSerialPort(), packet);
    }

    public void registerEvents() {
        eventRegistry.registerEvents(new UUIDListener());
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public List<MySerialPort> getCurrentConnections() {
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
}
