package de.standaloendmx.standalonedmxcontrolpro.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewManager {

    //TODO: Fix some errors so I can use every

    private final Map<Views, Node> loadedViews;
    Logger logger = LogManager.getLogger(ViewManager.class);

    public ViewManager() {
        loadedViews = new HashMap<>();
    }

    public void loadAllViews() {
        for (Views value : Views.values()) {
            try {
                logger.info("Loading " + value + " from " + value.getPath());
                loadedViews.put(value, FXMLLoader.load(Objects.requireNonNull(getClass().getResource(value.getPath()))));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Failed loading view: " + value.getPath(), e);
            }
        }
    }

    public Node getLoadedView(Views view) {
        return loadedViews.get(view);
    }

    public void setLoadedView(Views views, Parent contentArea) {
        loadedViews.put(views, contentArea);
    }
}
