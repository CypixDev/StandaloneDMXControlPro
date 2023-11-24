package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets;

import de.standaloendmx.standalonedmxcontrolpro.gui.deploy.AddNewInterfaceViewController;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;
import javafx.application.Platform;

public class UUIDPacket extends Packet {
    @Override
    public void read(CustomByteBuf buffer) {
        String uuid = buffer.readString();

        if(AddNewInterfaceViewController.instance != null){ //TODO: Pretty scetchy.... do better !!
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AddNewInterfaceViewController.instance.setDeviceId(uuid);
                }
            });
        }

    }

    @Override
    public void write(CustomByteBuf buffer) {

    }
}
