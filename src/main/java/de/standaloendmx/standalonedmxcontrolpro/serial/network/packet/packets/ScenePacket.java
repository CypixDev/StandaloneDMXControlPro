package de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties.TableStep;
import de.standaloendmx.standalonedmxcontrolpro.gui.edit.scene.MyScene;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.buffer.CustomByteBuf;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.Packet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScenePacket extends Packet {


    private MyScene scene;

    public ScenePacket() {
    }

    public ScenePacket(MyScene scene) {
        this.scene = scene;
    }

    public static int timeToMilliseconds(String time) {
        try{
            String[] units = time.split(":"); //split the time into hours, minutes, and seconds

            int hours = Integer.parseInt(units[0]);
            int minutes = Integer.parseInt(units[1]);
            int seconds = Integer.parseInt(units[2]);

            //convert everything to milliseconds and return the total
            System.out.println("    ret: "+(((hours * 60 * 60) + (minutes * 60) + seconds) * 1000));
            return ((hours * 60 * 60) + (minutes * 60) + seconds) * 1000;
        }catch (Exception e){
            return 0;
        }
    }

    public static String millisecondsToTime(int milliseconds) {
        int totalSeconds = milliseconds / 1000;
        int seconds = totalSeconds % 60;
        int totalMinutes = totalSeconds / 60;
        int minutes = totalMinutes % 60;
        int hours = totalMinutes / 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public MyScene getScene() {
        return scene;
    }

    @Override
    public void read(CustomByteBuf buffer) {
        if (buffer != null) {
            UUID uuid = UUID.fromString(buffer.readString());
            String name = buffer.readString();
            //TODO outdated model, do not use!
            int stepsSize = buffer.readInt();
            for (int i = 0; i < stepsSize; i++) {
                String fadeTime = millisecondsToTime(buffer.readInt());
                String holdTime = millisecondsToTime(buffer.readInt());
                int channelValuesSize = buffer.readInt();
                Map<Integer, Byte> channelValues = new HashMap<>();

                for (int j = 0; j < channelValuesSize; j++) {
                    short key = buffer.readShort();
                    byte value = buffer.readByte();
                    channelValues.put((int)key, value);
                }

                // Create a new TableStep object
                TableStep step = new TableStep(i, fadeTime, holdTime, channelValues);


                // Proceed with step ...
            }
        }
    }

    @Override
    public void write(CustomByteBuf buffer) {
        if (scene != null) {
            buffer.writeString(scene.getUuid().toString());
            buffer.writeString(scene.name.getText());

            buffer.writeByte((byte)scene.getColor().getColor().getRed());
            buffer.writeByte((byte)scene.getColor().getColor().getGreen());
            buffer.writeByte((byte)scene.getColor().getColor().getBlue());


            List<TableStep> steps = scene.getSteps();
            if (steps != null) {
                buffer.writeInt(steps.size()); //steps count

                System.out.println(scene.getUuid().toString()+" - "+scene.name.getText()+" - "+steps.size());
                for (TableStep step : steps) {
                    System.out.println("Step pos: "+step.getPos());

                    String fadeTime = step.getFadeTime();
                    System.out.println("Fade-time: "+step.getHoldTime());
                    buffer.writeInt(timeToMilliseconds(fadeTime));

                    String holdTime = step.getHoldTime();
                    System.out.println("Hold-time: "+step.getHoldTime());
                    buffer.writeInt(timeToMilliseconds(holdTime));


                    Map<Integer, Byte> channelValues = step.getChannelValues();
                    buffer.writeInt(channelValues.size());

                    System.out.println("    ChannelValues-Size: "+channelValues);

                    for (Map.Entry<Integer, Byte> entry : channelValues.entrySet()) {
                        buffer.writeShort(Short.parseShort(String.valueOf(entry.getKey())));
                        buffer.writeByte(entry.getValue());
                    }
                }
            }
        }
    }
}
