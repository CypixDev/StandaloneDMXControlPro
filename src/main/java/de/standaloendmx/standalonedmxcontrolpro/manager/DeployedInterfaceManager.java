package de.standaloendmx.standalonedmxcontrolpro.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.standaloendmx.standalonedmxcontrolpro.gui.deploy.DeployedInterface;
import de.standaloendmx.standalonedmxcontrolpro.main.StandaloneDMXControlPro;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeployedInterfaceManager {

    public static DeployedInterfaceManager instance;

    private List<DeployedInterface> deployedInterfaceList;
    private final File interfacesFile;


    public DeployedInterfaceManager() {
        instance = this;
        interfacesFile = new File(StandaloneDMXControlPro.instance.getFilesManager().getWorkFolder().getAbsolutePath()+"\\interfaces.json");
        deployedInterfaceList = new ArrayList<>();
        loadFromFilesInList();
    }

    public void loadFromFilesInList(){
        Gson gson = new Gson();

        try (Reader reader = new FileReader(interfacesFile)) {

            DeployedInterface[] deployedInterfacesArray = gson.fromJson(reader, DeployedInterface[].class);

            for (DeployedInterface deployedInterface : deployedInterfacesArray) {
                deployedInterfaceList.add(deployedInterface);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInFiles(DeployedInterface deployedInterface){
        deployedInterfaceList.add(deployedInterface);
        saveInFiles();
    }
    public void deleteFromList(DeployedInterface deployedInterface){
        deployedInterfaceList.remove(deployedInterface);
        saveInFiles();
    }

    public void saveInFiles(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = new FileWriter(interfacesFile)) {
            gson.toJson(deployedInterfaceList, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DeployedInterface> getDeployedInterfaceList() {
        return deployedInterfaceList;
    }
}
