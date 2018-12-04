package ca.warp7.rt.app;

import ca.warp7.rt.controller.utils.StageUtils;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        if (!System.getProperty("os.name").toLowerCase().startsWith("win")) {
            throw new UnsupportedOperationException("Only Windows is supported");
        }

        StageUtils.stage("/ca/warp7/rt/stages/main/App.fxml", "RestructuredTables", getClass());
    }

    static void launch0() {
        launch();
    }
}
