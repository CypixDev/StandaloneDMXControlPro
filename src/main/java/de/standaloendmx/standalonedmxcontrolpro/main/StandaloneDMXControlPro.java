package de.standaloendmx.standalonedmxcontrolpro.main;

import de.standaloendmx.standalonedmxcontrolpro.gui.main.MainApplication;
import javafx.application.Application;

public class StandaloneDMXControlPro {

    public static StandaloneDMXControlPro instance;


    public StandaloneDMXControlPro() {
        startGUI();
    }

    private void startGUI(){
        Application.launch(MainApplication.class);
    }

    public static void main(String[] args) {
        instance = new StandaloneDMXControlPro();
    }
}
