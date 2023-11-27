package de.standaloendmx.standalonedmxcontrolpro.gui.edit.group;

import de.standaloendmx.standalonedmxcontrolpro.gui.edit.ScenesViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyGroupBarLabel extends Label implements Initializable {

    private Logger logger = LogManager.getLogger(MyGroupContainer.class);

    private MyGroupContainer groupParent;

    @FXML
    private Label label;

    @FXML
    private FontIcon btnDelete;


    public MyGroupBarLabel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/edit/group/MyGroupBarLabelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setOnMouseClicked(event -> {
            label.getStyleClass().add("label_group_selected");
            if (event.getClickCount() == 2) {
                openNameInputPopup();
            }
        });

        btnDelete.setOnMouseClicked(event -> {
            if (showDeleteConfirmationPopup("Delete: " + label.getText() + " ?"))
                ScenesViewController.instance.removeGroupContainer(groupParent);
        });

    }

    public boolean showDeleteConfirmationPopup(String itemName) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Löschen bestätigen");
        confirmationDialog.setHeaderText("Sind Sie sicher, dass Sie '" + itemName + "' löschen möchten?");

        // Füge benutzerdefinierten Text zum Dialog hinzu (optional)
        Label customMessage = new Label("Dieser Vorgang kann nicht rückgängig gemacht werden.");
        DialogPane dialogPane = confirmationDialog.getDialogPane();

        dialogPane.getStylesheets().add(getClass().getResource("/gui/MainStyle.css").toExternalForm());
        dialogPane.getStylesheets().add(getClass().getResource("/gui/style/InputDialogStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        dialogPane.setExpandableContent(customMessage);

        // Füge OK- und Abbrechen-Buttons hinzu
        confirmationDialog.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Warte auf die Benutzerantwort
        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        return result == ButtonType.OK;
    }

    public MyGroupContainer getGroupParent() {
        return groupParent;
    }

    public void setGroupParent(MyGroupContainer groupParent) {
        this.groupParent = groupParent;
    }

    public void openNameInputPopup() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/gui/MainStyle.css").toExternalForm());
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/gui/style/InputDialogStyle.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("myDialog");
        dialog.setGraphic(null);
        dialog.setTitle("Enter name");
        dialog.setHeaderText("Enter group name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            if (!label.getText().isEmpty() && !label.getText().equals(" "))
                label.setText(name);
        });
    }

}
