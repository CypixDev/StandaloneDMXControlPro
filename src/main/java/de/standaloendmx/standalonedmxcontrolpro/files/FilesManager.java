package de.standaloendmx.standalonedmxcontrolpro.files;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FilesManager {

    private Logger logger;
    private final String userHome = System.getProperty("user.home");
    private  String documentsPath = userHome + "\\OneDrive\\Dokumente"; // Für Windows mit Onedrive

    private File workFolder;
    private File logFolder;
    private File fixtureLibraryFolder;

    public FilesManager() {
        logger = Logger.getLogger(FilesManager.class);
        if(!new File(documentsPath).exists()) documentsPath = userHome+"\\Documents"; //In case onedrive is not installed
        workFolder = new File(documentsPath+"\\SDMXCP");
        logFolder = new File(workFolder+"\\logs");
        fixtureLibraryFolder = new File(workFolder+"\\Fixture_Library");


    }

    public void init() {
        logger.info("Starting init file system");
        checkFolderPath(workFolder);
        checkFolderPath(logFolder);
        checkFolderPath(fixtureLibraryFolder);


        if(fixtureLibraryFolder.listFiles().length == 0){
            resetFixtureLibrary();
        }
    }

    public void resetFixtureLibrary(){
        logger.info("Resetting fixtures library");
        try {
            deleteFolder(fixtureLibraryFolder);
            checkFolderPath(fixtureLibraryFolder);
            unzip(getClass().getResourceAsStream("/data/fixtures_2023-09-11.zip"), fixtureLibraryFolder.getAbsolutePath());
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void checkFolderPath(File file){
        if(!file.exists()){
            logger.info("Created File: "+file.getName()+" | Result: "+file.mkdirs());
        }
    }

    public File getLogFolder() {
        return logFolder;
    }

    public File getFixtureLibraryFolder() {
        return fixtureLibraryFolder;
    }

    public File getWorkFolder() {
        return workFolder;
    }

    public String getDocumentsPath() {
        return documentsPath;
    }

    public String getUserHome() {
        return userHome;
    }

    public void unzip(InputStream zipFilePath, String destDirectory) throws IOException {
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
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
    private void deleteFolder(File folder) {
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
