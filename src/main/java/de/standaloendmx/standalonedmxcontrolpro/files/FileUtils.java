package de.standaloendmx.standalonedmxcontrolpro.files;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    static Logger logger = Logger.getLogger(FileUtils.class);

    public static void unzip(InputStream zipFilePath, String destDirectory) throws IOException {
        logger.info("Unzipping to " + destDirectory);
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
        logger.info("Deleting folder: " + folder);
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

    public static String getSVGPath(String path) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(FileUtils.class.getResourceAsStream(path));

            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("path");

            Element element = (Element) list.item(0);

            return element.getAttribute("d");


        } catch (Exception e) {
            logger.error(e);
        }
        return "";
    }

    public static void writeStringToFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();

            logger.info("Datei erfolgreich erstellt: " + file);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static String readStringFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return stringBuilder.toString();
    }


}
