package de.standaloendmx.standalonedmxcontrolpro.serial;


import com.fazecast.jSerialComm.SerialPort;

import java.io.UnsupportedEncodingException;

public class SerialTest {

    public static void main(String[] args) {
        // Finden Sie alle verfügbaren seriellen Ports
        SerialPort[] serialPorts = SerialPort.getCommPorts();

        if (serialPorts.length == 0) {
            System.out.println("Keine seriellen Ports gefunden.");
            return;
        }

        // Wählen Sie den ersten verfügbaren seriellen Port
        SerialPort serialPort = serialPorts[0];

        // Öffnen Sie den seriellen Port
        if (serialPort.openPort()) {
            System.out.println("Serieller Port geöffnet: " + serialPort.getSystemPortName());

            // Konfigurieren Sie den seriellen Port (Baudrate, Datenbits, etc.)
            serialPort.setBaudRate(9600);
            serialPort.setNumDataBits(8);
            serialPort.setNumStopBits(1);
            serialPort.setParity(SerialPort.NO_PARITY);

            try {
                // Beispiel: Sende Daten an den seriellen Port
                String dataToSend = "Hello Serial Port!";
                int bytesWritten = serialPort.writeBytes(dataToSend.getBytes(), dataToSend.length());

                System.out.println("Bytes geschrieben: " + bytesWritten);

                // Beispiel: Lese Daten vom seriellen Port
                byte[] buffer = new byte[1024];
                int bytesRead = serialPort.readBytes(buffer, buffer.length);

                if (bytesRead > 0) {
                    String receivedData = new String(buffer, 0, bytesRead);
                    System.out.println("Received data: " + receivedData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Schließen Sie den seriellen Port am Ende
                serialPort.closePort();
            }
        } else {
            System.err.println("Fehler beim Öffnen des seriellen Ports.");
        }
    }
}
