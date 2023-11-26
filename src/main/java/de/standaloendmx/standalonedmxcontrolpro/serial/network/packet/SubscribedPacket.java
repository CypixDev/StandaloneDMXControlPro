package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet;

public class SubscribedPacket {

    private Class<? extends Packet> subscribedPacket;

    public SubscribedPacket(Class<? extends Packet> subscribedPacket) {
        this.subscribedPacket = subscribedPacket;
    }

    public Class<? extends Packet> getSubscribedPacket() {
        return subscribedPacket;
    }
}
