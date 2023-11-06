package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;


public class MySlider extends VBox {

    private final Logger logger = LogManager.getLogger(MySlider.class);

    @FXML
    private Pane color;
    @FXML
    private Label value;

    @FXML
    private Label channel;

    @FXML
    private Slider slider;

    @FXML
    private Button button;

    public MySlider() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/SliderView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    public MySlider(int channel) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/fader/SliderView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }


        this.channel.setText(String.valueOf(channel));
    }

    @FXML
    private void initialize() {
        this.setVisible(true);
        this.setManaged(true);

        slider.valueProperty().addListener(e -> {
            value.setText(String.valueOf((int)slider.getValue()));

            if(button.getText().equals("❌")){
                setButtonActive();
            }
        });


        setButtonInActive();
        button.setOnAction(e -> {
            if(button.getText().equals("✅")){
                setButtonInActive();
            }else {
                setButtonActive();
            }
        });
    }

    private void setButtonActive(){
        button.setText("✅");
        button.getStyleClass().add("button_active");
        button.getStyleClass().remove("button_inactive");
    }

    private void setButtonInActive(){
        button.setText("❌");
        button.getStyleClass().add("button_inactive");
        button.getStyleClass().remove("button_active");
    }
}