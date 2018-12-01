package ca.warp7.tables.app;

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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/stages/main/App.fxml"));

        stage.setTitle("Restructured Tables");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/app-icon.png")));
        stage.setScene(new Scene(loader.load()));
        stage.setMinWidth(800);
        stage.setMinHeight(450);
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.show();
    }

    static void launch0() {
        launch();
    }
}
