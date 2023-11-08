package de.standaloendmx.standalonedmxcontrolpro.usb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class USBDeviceMonitor {
    public static void main(String[] args) {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            listUSBDevicesWindows();
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            listUSBDevicesUnix();
        } else {
            System.out.println("Unsupported operating system");
        }
    }

    // List USB devices on Windows
    private static void listUSBDevicesWindows() {
        try {
            Process process = Runtime.getRuntime().exec("wmic path Win32_PnPEntity WHERE \"ConfigManagerErrorCode = 0\" get DeviceID,Description");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // List USB devices on Unix-based systems (Linux, macOS)
    private static void listUSBDevicesUnix() {
        try {
            Process process = Runtime.getRuntime().exec("lsusb");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}