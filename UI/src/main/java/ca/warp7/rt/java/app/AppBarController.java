package ca.warp7.rt.java.app;

import javafx.fxml.FXML;

import static ca.warp7.rt.java.base.StageUtils.stage;

public class AppBarController {

    @FXML
    void onEventSelectAction() {
        stage("/ca/warp7/rt/stage/app/EventSelect.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        stage("/ca/warp7/rt/stage/app/SystemState.fxml", "System State", getClass());
    }

    @FXML
    void onScannerAction() {
        stage("/ca/warp7/rt/stage/scanner/Scanner.fxml", "Scanner", getClass());
    }

    @FXML
    void onPythonScripts() {
        stage("/ca/warp7/rt/stage/python/PythonScripts.fxml", "Python Scripts", getClass());
    }

    @FXML
    void initialize() {
    }
}
