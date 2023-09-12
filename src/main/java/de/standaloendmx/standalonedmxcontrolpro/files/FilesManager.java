package de.standaloendmx.standalonedmxcontrolpro.files;

import org.apache.log4j.Logger;

import java.io.*;

public class FilesManager {

    private final Logger logger;
    private final String userHome = System.getProperty("user.home");
    private  String documentsPath = userHome + "\\OneDrive\\Dokumente"; // Für Windows mit Onedrive

    private final File workFolder;
    private final File logFolder;
    private final File fixtureLibraryFolder;

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
            FileUtils.deleteFolder(fixtureLibraryFolder);
            checkFolderPath(fixtureLibraryFolder);
            FileUtils.unzip(getClass().getResourceAsStream("/data/fixtures_2023-09-11.zip"), fixtureLibraryFolder.getAbsolutePath());
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


}
