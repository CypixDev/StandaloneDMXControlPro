package de.standaloendmx.standalonedmxcontrolpro.gui.deploy;

import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;

public class DeployedInterface {

    private String name;
    private String group; //soon...
    private String uuid;

    private MySerialPort serialPort;


    public DeployedInterface(String name, String group, String uuid) {
        this.name = name;
        this.group = group;
        this.uuid = uuid;
    }

    public void setSerialPort(MySerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public MySerialPort getSerialPort() {
        return serialPort;
    }


    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getUuid() {
        return uuid;
    }
}
