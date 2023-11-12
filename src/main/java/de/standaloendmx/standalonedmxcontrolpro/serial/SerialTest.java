package de.standaloendmx.standalonedmxcontrolpro.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.exception.PacketRegistrationException;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.IPacketRegistry;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.registry.SimplePacketRegistry;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SerialTest {

    public static void main(String[] args) throws PacketRegistrationException {
        IPacketRegistry registry = new SimplePacketRegistry();
        registry.registerPacket(0, new TestPacket());


        // Finden Sie alle verfügbaren seriellen Ports
        SerialPort[] serialPorts = SerialPort.getCommPorts();

        if (serialPorts.length == 0) {
            System.out.println("Keine seriellen Ports gefunden.");
            return;
        }

        // Wählen Sie den ersten verfügbaren seriellen Port
        SerialPort serialPort = serialPorts[1];

        // Öffnen Sie den seriellen Port
        if (serialPort.openPort()) {
            System.out.println("Serieller Port geöffnet: " + serialPort.getSystemPortName());

            // Konfigurieren Sie den seriellen Port (Baudrate, Datenbits, etc.)
            serialPort.setBaudRate(9600);
            serialPort.setNumDataBits(8);
            serialPort.setNumStopBits(1);
            serialPort.setParity(SerialPort.NO_PARITY);

            try {
                new SerialServer().writeAndFlushPacket(serialPort, new TestPacket());
                System.out.println("Paket gesendet!");

                // Beispiel: Warten Sie auf Daten vom seriellen Port
                StringBuilder receivedDataBuilder = new StringBuilder();
                boolean endOfLineReceived = false;

                while (!endOfLineReceived) {
                    // Beispiel: Lese Daten vom seriellen Port
                    byte[] buffer = new byte[1024];
                    int bytesRead = serialPort.readBytes(buffer, buffer.length);

                    if (bytesRead > 0) {
                        String receivedData = new String(buffer, 0, bytesRead, "UTF-8");
                        receivedDataBuilder.append(receivedData);

                        // Überprüfen Sie, ob das Zeilenendezeichen empfangen wurde
                        if (receivedDataBuilder.toString().contains("\n")) {
                            endOfLineReceived = true;
                        }
                    }
                }

                // Extrahiere die empfangenen Daten ohne das Zeilenendezeichen
                String receivedData = receivedDataBuilder.toString().replace("\n", "");

                System.out.println("Received data: " + receivedData);



            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Schließen Sie den seriellen Port am Ende
                serialPort.closePort();
                System.out.println("Serieller Port geschlossen.");
            }
        } else {
            System.err.println("Fehler beim Öffnen des seriellen Ports.");
        }
    }
}
