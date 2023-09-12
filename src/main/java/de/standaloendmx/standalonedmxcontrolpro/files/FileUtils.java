package de.standaloendmx.standalonedmxcontrolpro.files;

import de.standaloendmx.standalonedmxcontrolpro.logging.LoggingManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    static Logger logger = Logger.getLogger(FileUtils.class);

    public static void unzip(InputStream zipFilePath, String destDirectory) throws IOException {
        logger.info("Unzipping to "+destDirectory);
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        try (ZipInputStream zipIn = new ZipInputStream(zipFilePath)) {
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }
    public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
    public static void deleteFolder(File folder) {
        logger.info("Deleting folder: "+folder);
        if (folder.exists()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Wenn es sich um ein Unterverzeichnis handelt, rekursiv löschen
                        deleteFolder(file);
                    } else {
                        // Wenn es sich um eine Datei handelt, löschen
                        file.delete();
                    }
                }
            }

            // Ordner selbst löschen
            folder.delete();
        }
    }


}
