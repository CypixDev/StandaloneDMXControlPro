package de.standaloendmx.standalonedmxcontrolpro.main;

import de.standaloendmx.standalonedmxcontrolpro.files.FilesManager;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import de.standaloendmx.standalonedmxcontrolpro.logging.LoggingManager;
import javafx.application.Application;
import org.apache.log4j.Logger;

public class StandaloneDMXControlPro {

    private static final String VERSION = "0.1-ALPHA";

    public static StandaloneDMXControlPro instance;

    /**TODO
    * ♾️
    *
    *
     */

    /**TODO-Improve
     * Beim färben der Felder beim Patchen nicht jedes mal das ganze feld resetten sondern nur das wo event exited oder ähnlcihes schmeißt
     *
     */

    private final Logger logger;

    private final FilesManager filesManager;

    private final LoggingManager loggingManager;


    public StandaloneDMXControlPro() {
        instance = this;
        loggingManager = new LoggingManager();
        loggingManager.setDebugging(true);
        logger = Logger.getLogger(StandaloneDMXControlPro.class);
        logger.info("Starting StandaloneDMXControlPro "+VERSION);
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

    public static void main(String[] args) {
        instance = new StandaloneDMXControlPro();
    }
}
