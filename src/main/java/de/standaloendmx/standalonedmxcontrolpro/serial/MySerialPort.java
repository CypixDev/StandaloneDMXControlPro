package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;

public class MySerialPort {

    private SerialPort serialPort;

    public MySerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    @Override
    public String toString() {
        return serialPort.getSystemPortName();
    }
}
