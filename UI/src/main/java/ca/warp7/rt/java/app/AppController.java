package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.StageController;
import ca.warp7.rt.java.base.StageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

import static ca.warp7.rt.java.base.StageUtils.stage;

public class AppController implements StageController {

    @Override
    public void setStage(Stage stage) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(true);
                stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE));
            }
        });
    }

    @FXML
    MenuButton newButton;

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
        Features.multiTabFeatures.forEach(multiTab -> {
            MenuItem item = new MenuItem();
            item.setText(multiTab.getFeatureName());
            item.setGraphic(StageUtils.icon(multiTab.getIconLiteral()));
            newButton.getItems().add(item);
        });
    }
}
