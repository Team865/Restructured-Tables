package ca.warp7.rt.java.core;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

public class Alerts {
    public static String getString(String title, String prompt, String defVal) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
//        dialog.setGraphic(new ImageView(Alerts.class.getResource("/app-icon.png").toString()));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(ButtonType::getText);
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }
}
