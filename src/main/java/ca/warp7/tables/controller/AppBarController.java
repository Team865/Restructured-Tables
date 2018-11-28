package ca.warp7.tables.controller;

import ca.warp7.tables.view.WebCamWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AppBarController {

    @FXML
    void onEventSettingsAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        System.out.println("Hi");
    }

    private WebCamWindow webCamWindow = new WebCamWindow();

    @FXML
    void onScanNowAction(){
        webCamWindow.start();
    }

    @FXML
    void initialize() {
    }
}
