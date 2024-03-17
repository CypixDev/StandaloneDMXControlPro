package de.standaloendmx.standalonedmxcontrolpro.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.standaloendmx.standalonedmxcontrolpro.gui.deploy.DeployedInterface;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeployedInterfaceManager {

    private static final Logger logger = LogManager.getLogger(DeployedInterface.class);

    public static DeployedInterfaceManager instance;
    private final File interfacesFile;
    private final List<DeployedInterface> deployedInterfaceList;


    public DeployedInterfaceManager() {
        instance = this;
        interfacesFile = new File(StandaloneDMXControlPro.instance.getFilesManager().getWorkFolder().getAbsolutePath() + "\\interfaces.json");
        deployedInterfaceList = new ArrayList<>();
        loadFromFilesInList();
    }

    public void loadFromFilesInList() {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(interfacesFile)) {

            DeployedInterface[] deployedInterfacesArray = gson.fromJson(reader, DeployedInterface[].class);

            Collections.addAll(deployedInterfaceList, deployedInterfacesArray);

        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void saveInFiles(DeployedInterface deployedInterface) {
        deployedInterfaceList.add(deployedInterface);
        saveInFiles();
    }

    public void deleteFromList(DeployedInterface deployedInterface) {
        deployedInterfaceList.remove(deployedInterface);
        saveInFiles();
    }

    public void saveInFiles() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = new FileWriter(interfacesFile)) {
            gson.toJson(deployedInterfaceList, writer);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public List<DeployedInterface> getDeployedInterfaceList() {
        return deployedInterfaceList;
    }
}
