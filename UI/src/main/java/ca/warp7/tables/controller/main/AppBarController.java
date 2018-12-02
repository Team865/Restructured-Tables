package ca.warp7.tables.controller.main;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import static ca.warp7.tables.controller.utils.StageUtils.stage;

public class AppBarController {

    @FXML
    void onEventSelectAction() {
        stage("/ca/warp7/tables/stages/EventSelect.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        stage("/ca/warp7/tables/stages/SystemState.fxml", "System State", getClass());
    }

    @FXML
    void onScannerAction() {
        stage("/ca/warp7/tables/stages/Scanner.fxml", "Scanner", getClass());
    }

    @FXML
    void onLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(null);
    }


    @FXML
    void onNewViewAction() {
        stage("/ca/warp7/tables/stages/NewView.fxml", "New View", getClass());
    }

    @FXML
    void onPythonAnalysis() {
        stage("/ca/warp7/tables/stages/Editor.fxml", "Python", getClass());
    }

    @FXML
    void initialize() {
    }
}
