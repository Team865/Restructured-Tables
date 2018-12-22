package ca.warp7.rt.core;

import ca.warp7.rt.core.env.EnvUtils;
import ca.warp7.rt.core.feature.FeatureUtils;
import javafx.application.Application;
import javafx.stage.Stage;

public class RestructuredTables extends Application {
    static void launch0() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        EnvUtils.ensureWindowsOS();
        FeatureUtils.showStage("/ca/warp7/rt/core/app/App.fxml", "Restructured Tables");
    }
}
