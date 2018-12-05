package ca.warp7.rt.java.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class StageUtils {
    public static void stage(String resFile, String windowTitle, Class caller) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(caller.getResource(resFile));
        stage.setTitle(windowTitle);
        stage.getIcons().add(new Image(caller.getResourceAsStream("/app-icon.png")));

        try {
            stage.setScene(new Scene(loader.load()));
            Object controller = loader.getController();
            if (controller instanceof StageController) {
                ((StageController) controller).setStage(stage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    public static Parent node(String resFile, Class caller) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(caller.getResource(resFile));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new HBox();
        }
    }
}
