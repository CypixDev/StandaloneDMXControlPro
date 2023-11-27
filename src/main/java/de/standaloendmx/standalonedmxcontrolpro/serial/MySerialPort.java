package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;

public class MySerialPort {

    private SerialPort serialPort;
    private SerialPortInboundHandler serialPortInboundHandler;
    private String uuid;


    public MySerialPort(SerialPort serialPort, SerialPortInboundHandler serialPortInboundHandler) {
        this.serialPort = serialPort;
        this.serialPortInboundHandler = serialPortInboundHandler;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
