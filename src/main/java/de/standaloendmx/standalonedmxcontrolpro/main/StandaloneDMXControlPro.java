package de.standaloendmx.standalonedmxcontrolpro.main;

import de.standaloendmx.standalonedmxcontrolpro.files.FilesManager;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureManager;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import de.standaloendmx.standalonedmxcontrolpro.logging.LoggingManager;
import javafx.application.Application;
import org.apache.log4j.Logger;

public class StandaloneDMXControlPro {

    private static final String VERSION = "0.1-ALPHA";
    private static final boolean DEBUGGING = false;

    public static StandaloneDMXControlPro instance;

    /**TODO
    * ♾️
    * try catch hinzufügen bei fixture aus datei laden und in die directory vllt auch
    * Replace fixture names with fixtureKey from .json maybe
     */

    /**TODO-Improve
     * Beim färben der Felder beim Patchen nicht jedes mal das ganze feld resetten sondern nur das wo event exited oder ähnlcihes schmeißt
     * Die suche bei der Fixture Lib verbessern
     *
     */

    private final Logger logger;

    private final FilesManager filesManager;

    private final LoggingManager loggingManager;

    private final FixtureManager fixtureManager;


    public StandaloneDMXControlPro() {
        instance = this;

        loggingManager = new LoggingManager();
        loggingManager.setDebugging(DEBUGGING);
        logger = Logger.getLogger(StandaloneDMXControlPro.class);
        logger.info("Starting StandaloneDMXControlPro "+VERSION);
        logger.info("Debugging: "+DEBUGGING);


        fixtureManager = new FixtureManager();

        startGUI();


        filesManager = new FilesManager();
        filesManager.init(); //Creating paths
    }

    private void startGUI(){
        new Thread(() -> Application.launch(MainApplication.class)).start();
    }


    public LoggingManager getLoggingManager() {
        return loggingManager;
    }

    public FilesManager getFilesManager() {
        return filesManager;
    }

    public FixtureManager getFixtureManager() {
        return fixtureManager;
    }

    public static void main(String[] args) {
        instance = new StandaloneDMXControlPro();
    }
}
