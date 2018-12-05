package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.StageController;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

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
    void initialize() {
    }
}
