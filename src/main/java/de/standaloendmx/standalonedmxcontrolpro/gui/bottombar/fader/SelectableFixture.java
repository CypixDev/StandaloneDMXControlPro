package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader;

import de.standaloendmx.standalonedmxcontrolpro.files.FileUtils;
import de.standaloendmx.standalonedmxcontrolpro.patch.PatchFixture;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SelectableFixture extends VBox {

    private final Logger logger = LogManager.getLogger(MySlider.class);

    @FXML
    private ImageView ivFixtureImage;
    @FXML
    private Label lFixtureName;

    public final PatchFixture patchFixture;


    public SelectableFixture(PatchFixture patchFixture) {
        this.patchFixture = patchFixture;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/fader/FixtureView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    @FXML
    private void initialize() {

        String svg = FileUtils.getSVGPath("/gui/img/icons/fixture/" + patchFixture.getFixture().getCategories().get(0).getFileName() + ".svg");

        SVGPath svgPath = new SVGPath();
        svgPath.setContent(svg);


        //TODO: scale so full icon is shown....
        svgPath.setFill(Color.BLACK);
        svgPath.setScaleX(3);
        svgPath.setScaleY(3);

        SnapshotParameters snapshotParams = new SnapshotParameters();
        snapshotParams.setFill(Color.TRANSPARENT);

        WritableImage image = new WritableImage(50, 50); // Breite und Höhe anpassen
        image = svgPath.snapshot(snapshotParams, image);


        ivFixtureImage.setImage(image);
        lFixtureName.setText(patchFixture.getName());
    }
}