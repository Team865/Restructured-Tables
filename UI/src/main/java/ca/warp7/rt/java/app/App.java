package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        String os = System.getProperty("os.name").toLowerCase();
        if (!os.startsWith("win")) throw new RuntimeException("Only Windows is supported");
        FeatureUtils.showStage("/ca/warp7/rt/stage/app/App.fxml", "Restructured Tables", getClass());
    }

    public static void launch0() {
        launch();
    }
}
