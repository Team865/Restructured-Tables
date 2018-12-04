package ca.warp7.rt.java.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class EventSelectController {
    @FXML
    void onDeleteAction(){
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Delete Event 2018iri? There is no going back!",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
    }

    @FXML
    void onBlueAllianceEventAction(){
        TextInputDialog dialog = new TextInputDialog("Enter The Blue Alliance key");
        dialog.showAndWait();
    }
}
