package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fixtureselect.FixtureSelectViewController;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fixtureselect.SelectableFixture;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class FaderViewController implements Initializable {

    public static FaderViewController instance;


    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;


        Platform.runLater(() -> {
            for (int i = 0; i < 512; i++) {
                hBox.getChildren().add(new MyFader(i + 1));
            }
        });
        for (Node child : hBox.getChildren()) {
            MyFader fader = (MyFader) child;
        }

        //96+5+71
        //scrollPane.prefWidthProperty().bind(MainApplication.mainStage.widthProperty().subtract(96+5+71));
        //scrollPane.setPrefWidth(splitPane.getWidth() / 2);
        scrollPane.setContent(hBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Zeige die horizontale Scrollbar immer an
        scrollPane.setFitToWidth(true); // Passe den Inhalt der ScrollPane an die Breite an
        scrollPane.setFitToHeight(true);

    }

    public void updateSliders() {
        Set<Integer> shownFaders = new HashSet<>();
        for (SelectableFixture fixture : FixtureSelectViewController.instance.getFixtures()) {
            if (fixture.getStyleClass().contains("selected")) {
                PatchFixture patch = fixture.patchFixture;
                for (int i = patch.getChannel(); i < patch.getSize() + patch.getChannel(); i++) {
                    shownFaders.add(i);
                }
            }
        }
        if (shownFaders.isEmpty()) {
            for (int i = 0; i < 512; i++) {
                hBox.getChildren().get(i).setManaged(true);
                hBox.getChildren().get(i).setVisible(true);
            }
        } else {
            for (int i = 0; i < 512; i++) {
                if (shownFaders.contains(i)) {
                    hBox.getChildren().get(i).setManaged(true);
                    hBox.getChildren().get(i).setVisible(true);
                } else {
                    hBox.getChildren().get(i).setManaged(false);
                    hBox.getChildren().get(i).setVisible(false);
                }
            }
        }
    }

    public void setSliders(Map<Integer, Byte> channelValues) {

        for (Map.Entry<Integer, Byte> entry : channelValues.entrySet()) {
            MyFader slider = (MyFader) hBox.getChildren().get(entry.getKey());
            slider.setManaged(true);
            slider.setVisible(true);
            int val = entry.getValue() & 0xFF;
            slider.setSliderValue(val);
            slider.setButtonActive();
        }

        for (int i = 0; i < 512; i++) {
            MyFader fader = (MyFader) hBox.getChildren().get(i);
            //DEBUG: System.out.println("i: " + i + ", Fader: " + fader.channel.getText() +", Style classes: "+fader.button.getStyleClass().size());

            if (!channelValues.containsKey(i)) {
                fader.setSliderValue(0);
                fader.setButtonInActive();
            }
        }
    }

    public void blindAllFaders() {
        for (Node child : hBox.getChildren()) {
            MyFader fader = (MyFader) child;
            fader.button.getStyleClass().remove(1);
        }
    }
}
