package ca.warp7.tables.controller.main;

import ca.warp7.tables.view.WebCamWindow;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import static ca.warp7.tables.controller.Misc.openWindow;

public class AppBarController {

    @FXML
    void onEventSelectAction() {
        openWindow("/stages/event_select.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        openWindow("/stages/system_state.fxml", "System State", getClass());
    }


    private WebCamWindow webCamWindow = new WebCamWindow();

    @FXML
    void onOldScannerAction() {
        webCamWindow.start();
    }

    @FXML
    void onScannerAction() {
    }

    @FXML
    void onLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }


    @FXML
    void onNewViewAction() {
        openWindow("/stages/new_view.fxml", "New View", getClass());
    }

    @FXML
    void initialize() {
    }
}
