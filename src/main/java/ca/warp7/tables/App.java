package ca.warp7.tables;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Restructured Tables");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/app-icon.png")));
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/stages/main/App.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void launch0() {
        launch();
    }
}
