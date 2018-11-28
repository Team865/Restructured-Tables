package ca.warp7.tables;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class TablesApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Restructured Tables");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/app-icon.png")));
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxml/Main.fxml"));

        try {
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.show();
    }

    static void launch0() {
        launch();
    }
}
