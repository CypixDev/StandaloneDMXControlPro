package de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fixtureselect;

import de.standaloendmx.standalonedmxcontrolpro.files.FileUtils;
import de.standaloendmx.standalonedmxcontrolpro.gui.bottombar.fader.MyFader;
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

    public final PatchFixture patchFixture;
    private final Logger logger = LogManager.getLogger(MyFader.class);
    @FXML
    private ImageView ivFixtureImage;
    @FXML
    private Label lFixtureName;


    public SelectableFixture(PatchFixture patchFixture) {
        this.patchFixture = patchFixture;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/bottombar/fixtureselect/FixtureView.fxml"));
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
        System.out.println(svgPath.getLayoutBounds().toString());

        //TODO: scale so full icon is shown.... //Fixxed for now..
        svgPath.setFill(Color.BLACK);
        svgPath.setScaleX(50.0 / svgPath.getLayoutBounds().getWidth());
        svgPath.setScaleY(50.0 / svgPath.getLayoutBounds().getHeight());

        SnapshotParameters snapshotParams = new SnapshotParameters();
        snapshotParams.setFill(Color.TRANSPARENT);

        WritableImage image = new WritableImage(50, 50); // Breite und Höhe anpassen
        image = svgPath.snapshot(snapshotParams, image);


        ivFixtureImage.setImage(image);
        lFixtureName.setText(patchFixture.getName());
    }
}