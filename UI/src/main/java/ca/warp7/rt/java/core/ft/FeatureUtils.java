package ca.warp7.rt.java.core.ft;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class FeatureUtils {
    public static void showStage(String resFile, String windowTitle) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Class caller = getCaller();
        loader.setLocation(caller.getResource(resFile));
        stage.setTitle(windowTitle);
        stage.getIcons().add(new Image(caller.getResourceAsStream("/app-icon.png")));
        try {
            stage.setScene(new Scene(loader.load()));
            Object controller = loader.getController();
            if (controller instanceof FeatureStage) {
                ((FeatureStage) controller).setStage(stage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    public static Parent loadParent(String resFile) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getCaller().getResource(resFile));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new HBox();
        }
    }

    public static <T> Parent loadParent(String resFile, Consumer<T> controllerCallback) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getCaller().getResource(resFile));
            Parent parent = loader.load();
            if (controllerCallback != null) controllerCallback.accept(loader.getController());
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
            return new HBox();
        }
    }

    private static Class getCaller() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
        try {
            return Class.forName(caller.getClassName());
        } catch (ClassNotFoundException e) {
            return FeatureUtils.class;
        }
    }
}
