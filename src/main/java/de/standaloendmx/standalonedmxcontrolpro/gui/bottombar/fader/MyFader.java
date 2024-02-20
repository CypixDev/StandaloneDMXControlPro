package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.properties.StepsViewController;
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


public class MyFader extends VBox {

    private final Logger logger = LogManager.getLogger(MyFader.class);
    @FXML
    public Label channel;
    @FXML
    public Button button;
    @FXML
    private Pane color;
    @FXML
    private Label value;
    @FXML
    private Slider slider;


    public MyFader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/SliderView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    public MyFader(int channel) {
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
            value.setText(String.valueOf((int) slider.getValue()));

            if (button.getStyleClass().contains("button_inactive")) {
                setButtonActive();
            }

            StepsViewController.instance.channelValueUpdate(channel, value);
        });


        button.setOnAction(e -> {
            if (button.getStyleClass().contains("button_active")) {
                setButtonInActive();
            } else {
                setButtonActive();
            }
        });
    }

    public void setButtonActive() {
        button.setText("✅");
        if (button.getStyleClass().size() >= 2) //has to be so complicated because just remove with string not working....
            button.getStyleClass().remove(1);

        button.getStyleClass().add("button_active");
    }

    public void setButtonInActive() {
        button.setText("❌");
        if (button.getStyleClass().size() >= 2)
            button.getStyleClass().remove(1);

        button.getStyleClass().add("button_inactive");

    }

    public void setSliderValue(int value) {
        if (value <= 512 && value >= 0)
            slider.setValue(value);
    }

    public int getChannel() {
        return Integer.parseInt(this.channel.getText());
    }

    public void update() {
        this.setManaged(this.isManaged());
        this.setVisible(this.isVisible());
        this.setSliderValue((int)this.slider.getValue());
        if (this.button.getStyleClass().contains("button_active")) {
            this.setButtonActive();
        } else {
            this.setButtonInActive();
        }
    }
}