package ca.warp7.tables.controller;

import ca.warp7.tables.view.WebCamWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class AppBarController {

    void openWindow(String resFile, String windowTitle){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(resFile));
            stage.setTitle(windowTitle);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/app-icon.png")));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEventConfigureAction() {
        openWindow("/stages/event_config/Main.fxml", "Configure Event");
    }

    @FXML
    void onSystemStateAction() {
        openWindow("/stages/system_state/Main.fxml", "System State");
    }


    private WebCamWindow webCamWindow = new WebCamWindow();

    @FXML
    void onScannerAction() {
        webCamWindow.start();
    }

    @FXML
    void onImportDataAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }

    @FXML
    void initialize() {
    }
}
