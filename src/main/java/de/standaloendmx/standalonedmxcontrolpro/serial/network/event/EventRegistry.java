/*
 * Copyright (c) 2021, Pierre Maurice Schwang <mail@pschwang.eu> - MIT
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package de.standaloendmx.standalonedmxcontrolpro.serial.network.event;


import de.standaloendmx.standalonedmxcontrolpro.serial.MySerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class EventRegistry {

    private final Set<RegisteredPacketSubscriber> subscribers = new HashSet<>();

    public void registerEvents(Object holder) {
        subscribers.add(new RegisteredPacketSubscriber(holder));
    }

    //All packages arrive here
    public void invoke(Packet packet, MySerialPort ctx) {
/*        for (SerialPortInboundHandler currentConnection : SerialServer.getInstance().getCurrentConnections()) {
            if(currentConnection.getSerialPort().equals(ctx)){ //Checking if right port
                for (SubscribedPacket subscribedPacket : currentConnection.getSubscribedPackets()) {
                    if(subscribedPacket.getSubscribedPacket().getClass().equals(packet.getClass())){
                    }
                }
                break;
            }
        }*/

        try {
            for (RegisteredPacketSubscriber subscriber : subscribers) {
                subscriber.invoke(packet, ctx);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
