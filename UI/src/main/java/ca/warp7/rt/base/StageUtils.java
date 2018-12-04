package ca.warp7.rt.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StageUtils {
    public static void stage(String resFile, String windowTitle, Class caller) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(caller.getResource(resFile));

            stage.setTitle(windowTitle);
            stage.getIcons().add(new Image(caller.getResourceAsStream("/app-icon.png")));
            stage.setScene(new Scene(loader.load()));

            Object controller = loader.getController();
            if (controller instanceof StageController) {
                ((StageController) controller).setStage(stage);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
