package ca.warp7.tables.controller.main;

import ca.warp7.tables.controller.Misc;
import ca.warp7.tables.view.WebCamWindow;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class AppBarController {

    @FXML
    void onEventSelectAction() {
        Misc.openWindow("/stages/event_select.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        Misc.openWindow("/stages/system_state.fxml", "System State", getClass());
    }


    private WebCamWindow webCamWindow = new WebCamWindow();

    @FXML
    void onScannerAction() {
        webCamWindow.start();
    }

    @FXML
    void onLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }

    @FXML
    void initialize() {
    }
}
