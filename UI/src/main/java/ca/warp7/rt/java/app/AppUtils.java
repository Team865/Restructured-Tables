package ca.warp7.rt.java.app;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Optional;
import java.util.function.Function;

public class AppUtils {
    static AppController instance;

    public static void setStatusMessage(String statusMessage) {
        if (instance != null) {
            instance.statusMessageLabel.setText(statusMessage);
        }
    }

    public static String getString(String title, String prompt, String defVal, Function<String, Boolean> validator) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.initOwner(instance.getAppStage());

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setStyle("-fx-padding: 10");

        Label label = new Label(prompt);
        label.setWrapText(true);

        TextField field = new TextField();
        field.setText(defVal.trim());

        if (validator != null) {
            Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
            field.textProperty().addListener((observable, oldValue, newValue)
                    -> okButton.setDisable(!validator.apply(newValue.trim())));
        }

        vBox.getChildren().addAll(label, field);
        vBox.setMinWidth(400);

        dialog.getDialogPane().setContent(vBox);

        Platform.runLater(field::requestFocus);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) return field.getText();
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}
