package ca.warp7.tables.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Misc {
    public static void openWindow(String resFile, String windowTitle, Class caller){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(caller.getResource(resFile));
            stage.setTitle(windowTitle);
            stage.getIcons().add(new Image(caller.getResourceAsStream("/app-icon.png")));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
