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

    /**
     * Starts a scheduler that periodically executes the scan method and refreshes the list and status in the DeployViewController.
     */
    private void startScanner() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            scan();
            Platform.runLater(() -> {
                if (DeployViewController.instance != null)
                    DeployViewController.instance.refreshListAndStatus();
            });
        }, 1, 2, TimeUnit.SECONDS);
    }

    /**
     * Scans for available serial ports and connects to Arduino Uno, ESP32, or other supported devices.
     *
     * @return true if a supported device is detected and connected, false otherwise
     */
    public boolean scan() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) return false;

        for (SerialPort port : ports) {
            if (port.openPort()) {
                //if(port.getDescriptivePortName().startsWith("USB-SERIAL CH340")){
                if (port.getDescriptivePortName().startsWith("Arduino Uno") || port.getDescriptivePortName().startsWith("Silicon Labs CP210x") || port.getDescriptivePortName().startsWith("Serielles USB-Gerät")) {
                    logger.info("Connected to Arduino nano/ESP32 (" + port.getSystemPortName() + ")");

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

    /**
     * Configures the given serial port with the required settings and returns a SerialPortInboundHandler
     * for communication with the port.
     *
     * @param port the serial port to configure
     * @return the serial port inbound handler for the configured port
     */
    private SerialPortInboundHandler configPort(SerialPort port) {
        port.setBaudRate(115200);
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

    /**
     * Starts communication with the given serial port.
     *
     * @param mySerialPort the serial port to start communication with
     * @return the serial port inbound handler for the started communication
     * @deprecated This method is deprecated and will be removed in future versions. Use a different method for communication.
     */
    @Deprecated
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

    /**
     * Writes a packet to the serial port and flushes the data. The method encodes the packet
     * using the given packetEncoder and writes it to the serial port byte by byte.
     *
     * @param serialPort the serial port to write the packet to
     * @param packet     the packet to be written
     * @return true if the packet was written and flushed successfully, false otherwise
     * @throws Exception if there is an error in encoding or writing the packet
     */
    public boolean writeAndFlushPacket(SerialPort serialPort, Packet packet) throws Exception {
        CustomByteBuf buf = new CustomByteBuf(8);

        packetEncoder.encode(serialPort, packet, buf);

        byte[] toSend = buf.array();
        for (int i = 0; i < toSend.length; i += 64) {
            if ((i + 64) > toSend.length) {
                serialPort.writeBytes(toSend, toSend.length - i, i);
            } else {
                serialPort.writeBytes(toSend, 64, i);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }
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
