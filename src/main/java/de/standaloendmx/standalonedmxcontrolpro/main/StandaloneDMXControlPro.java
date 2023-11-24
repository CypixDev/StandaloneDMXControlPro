package de.standaloendmx.standalonedmxcontrolpro.main;

import de.standaloendmx.standalonedmxcontrolpro.files.FilesManager;
import de.standaloendmx.standalonedmxcontrolpro.fixture.FixtureManager;
import de.standaloendmx.standalonedmxcontrolpro.gui.ViewManager;
import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import de.standaloendmx.standalonedmxcontrolpro.logging.LoggingManager;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchManager;
import de.standaloendmx.standalonedmxcontrolpro.serial.SerialServer;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.exception.PacketRegistrationException;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.DebugPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.PingPacket;
import de.standaloendmx.standalonedmxcontrolpro.serial.network.packet.packets.UUIDPacket;
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

    /**
     * TODO-Improve
     * Beim färben der Felder beim Patchen nicht jedes mal das ganze feld resetten sondern nur das wo event exited oder ähnlcihes schmeißt
     * Die suche bei der Fixture Lib verbessern
     * <p>
     * Alle views, besonders patchview beim start laden
     * <p>
     * Nach dem placen von einem Patch grid updaten um nicht zurückgesetzte felder zu resetten
     *
     * Das Content feld muss zu einem einfachen pane werden und was applied wird entscheidet was dann passiert
     *
     * Design von Dialogen studieren ?!
     */

    private final Logger logger;

    private final FilesManager filesManager;

    private final LoggingManager loggingManager;

    private final FixtureManager fixtureManager;
    private final ViewManager viewManager;
    private final PatchManager patchManager;
    private final SerialServer serialServer;


    public StandaloneDMXControlPro() {
        instance = this;

        loggingManager = new LoggingManager();
        loggingManager.setDebugging(DEBUGGING);
        logger = Logger.getLogger(StandaloneDMXControlPro.class);
        logger.info("Starting StandaloneDMXControlPro " + VERSION);
        logger.info("Debugging: " + DEBUGGING);

        viewManager = new ViewManager();
        fixtureManager = new FixtureManager();
        patchManager = new PatchManager();

        startGUI();


        filesManager = new FilesManager();
        filesManager.init(); //Creating paths


        serialServer = new SerialServer();
        registerPackets();

    }

    private void registerPackets() {
        try {
            serialServer.getPacketRegistry().registerPacket(0, new PingPacket());
            serialServer.getPacketRegistry().registerPacket(1, new UUIDPacket());
            serialServer.getPacketRegistry().registerPacket(2, new DebugPacket());
        } catch (PacketRegistrationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        instance = new StandaloneDMXControlPro();
    }

    private void startGUI() {
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

    public ViewManager getViewManager() {
        return viewManager;
    }

    public PatchManager getPatchManager() {
        return patchManager;
    }

    public SerialServer getSerialServer() {
        return serialServer;
    }
}
