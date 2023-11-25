package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets;

import de.standaloendmx.standalonedmxcontrolpro.gui.deploy.AddNewInterfaceViewController;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import javafx.application.Platform;

public class UUIDPacket extends Packet {

    private String uuid;

    @Override
    public void read(CustomByteBuf buffer) {
        String uuid = buffer.readString();
        this.uuid = uuid;
    }

    @Override
    public void write(CustomByteBuf buffer) {

    }

    public String getUuid() {
        return uuid;
    }
}
