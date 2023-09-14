package de.standaloendmx.standalonedmxcontrolpro.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {

    //TODO: Fix some errors so I can use every

    Logger logger = LogManager.getLogger(ViewManager.class);

    private final Map<Views, Node> loadedViews;

    public ViewManager() {
        loadedViews = new HashMap<>();
    }

    public void loadAllViews(){
        for (Views value : Views.values()) {
            try {
                logger.info("Loading "+value+" from "+value.getPath());
                loadedViews.put(value, FXMLLoader.load(getClass().getResource(value.getPath())));
            } catch (IOException e) {
                logger.error("Failed loading view: "+value.getPath(), e);
            }
        }
    }

    public Node getLoadedView(Views view){
        return loadedViews.get(view);
    }

}
