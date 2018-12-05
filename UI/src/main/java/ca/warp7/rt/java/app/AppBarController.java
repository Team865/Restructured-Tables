package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.StageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.kordamp.ikonli.javafx.FontIcon;

import static ca.warp7.rt.java.base.StageUtils.stage;

public class AppBarController {

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
