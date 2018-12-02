package ca.warp7.tables.app;

import ca.warp7.tables.controller.utils.StageUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        if (!System.getProperty("os.name").toLowerCase().startsWith("win")) {
            throw new UnsupportedOperationException("Only Windows is supported");
        }

        StageUtils.stage("/ca/warp7/tables/stages/main/App.fxml", "RestructuredTables", getClass());
    }

    static void launch0() {
        launch();
    }
}
