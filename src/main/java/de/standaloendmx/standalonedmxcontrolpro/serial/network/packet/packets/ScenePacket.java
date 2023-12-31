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
        String[] units = time.split(":"); //split the time into hours, minutes, and seconds

        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int seconds = Integer.parseInt(units[2]);

        //convert everything to milliseconds and return the total
        return ((hours * 60 * 60) + (minutes * 60) + seconds) * 1000;
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
            int stepsSize = buffer.readInt();
            for (int i = 0; i < stepsSize; i++) {
                String fadeTime = millisecondsToTime(buffer.readInt());
                String holdTime = millisecondsToTime(buffer.readInt());
                int channelValuesSize = buffer.readInt();
                Map<Integer, Integer> channelValues = new HashMap<>();

                for (int j = 0; j < channelValuesSize; j++) {
                    int key = buffer.readInt();
                    int value = buffer.readInt();
                    channelValues.put(key, value);
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
            List<TableStep> steps = scene.getSteps();
            if (steps != null) {
                buffer.writeInt(steps.size());
                for (TableStep step : steps) {
                    if (step != null) {
                        String fadeTime = step.getFadeTime();
                        if (fadeTime != null) {
                            buffer.writeInt(timeToMilliseconds(fadeTime));
                        }

                        String holdTime = step.getHoldTime();
                        if (holdTime != null) {
                            buffer.writeInt(timeToMilliseconds(holdTime));
                        }

                        Map<Integer, Integer> channelValues = step.getChannelValues();
                        if (channelValues != null) {
                            buffer.writeInt(channelValues.size());
                            for (Map.Entry<Integer, Integer> entry : channelValues.entrySet()) {
                                if (entry != null) {
                                    buffer.writeInt(entry.getKey());
                                    buffer.writeInt(entry.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
