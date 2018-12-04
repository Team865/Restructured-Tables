package ca.warp7.rt.java.app;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import static ca.warp7.rt.java.base.StageUtils.stage;

public class AppBarController {

    @FXML
    void onEventSelectAction() {
        stage("/ca/warp7/rt/stages/EventSelect.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        stage("/ca/warp7/rt/stages/SystemState.fxml", "System State", getClass());
    }

    @FXML
    void onScannerAction() {
        stage("/ca/warp7/rt/stages/Scanner.fxml", "Scanner", getClass());
    }

    @FXML
    void onLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }

    @FXML
    void onPythonScripts() {
        stage("/ca/warp7/rt/stages/PythonScripts.fxml", "Python Scripts", getClass());
    }

    @FXML
    void initialize() {
    }
}
