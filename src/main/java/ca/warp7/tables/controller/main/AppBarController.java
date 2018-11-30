package ca.warp7.tables.controller.main;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import static ca.warp7.tables.controller.utils.StageUtils.stage;

public class AppBarController {

    @FXML
    void onEventSelectAction() {
        stage("/stages/EventSelect.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        stage("/stages/SystemState.fxml", "System State", getClass());
    }

    @FXML
    void onScannerAction() {
        stage("/stages/Scanner.fxml", "Scanner", getClass());
    }

    @FXML
    void onLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }


    @FXML
    void onNewViewAction() {
        stage("/stages/NewView.fxml", "New View", getClass());
    }

    @FXML
    void initialize() {
    }
}
