package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.handler.SerialPortInboundHandler;

public class MySerialPort {

    private SerialPort serialPort;
    private SerialPortInboundHandler serialPortInboundHandler;


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


}
