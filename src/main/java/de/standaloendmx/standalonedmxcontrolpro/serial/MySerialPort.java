package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;

public class MySerialPort {

    private final SerialPort serialPort;
    private final SerialPortInboundHandler serialPortInboundHandler;
    private String uuid;
    private boolean added;


    public MySerialPort(SerialPort serialPort, SerialPortInboundHandler serialPortInboundHandler) {
        this.serialPort = serialPort;
        this.serialPortInboundHandler = serialPortInboundHandler;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public SerialPortInboundHandler getSerialPortInboundHandler() {
        return serialPortInboundHandler;
    }

    @Override
    public String toString() {
        return serialPort.getSystemPortName();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
