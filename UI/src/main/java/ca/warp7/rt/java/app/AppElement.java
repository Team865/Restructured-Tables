package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.FeatureIcon;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

class AppElement {

    static HBox tabUIFromAction(AppActionTab tab) {

        FeatureItemTab action = tab.getFeatureItemTab();

        HBox outer = new HBox();

        HBox inner = new HBox();
        inner.setPrefWidth(20);
        inner.setAlignment(Pos.CENTER);

        FontIcon icon = new FeatureIcon(action.getIconLiteral());

        inner.getChildren().add(icon);
        tab.setIcon(icon);

        outer.setSpacing(10);
        outer.getChildren().add(inner);
        outer.getChildren().add(new Label(action.getActionTitle()));

        return outer;
    }

    static void showMemoryAlert() {
        String mem = String.format("Memory: %.2f MB", (Runtime.getRuntime().totalMemory()
                - Runtime.getRuntime().freeMemory()) / 1000000.0);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mem, ButtonType.OK);
        new Thread(() -> Runtime.getRuntime().gc()).start();
        alert.showAndWait();
    }

    static AppActionTab getTeamLogo() {
        int height = 64;
        ImageView teamLogo = new ImageView();
        teamLogo.setImage(new Image("/warp7.png"));
        teamLogo.setPreserveRatio(true);
        teamLogo.setFitHeight(height);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(teamLogo);
        return new AppActionTab(hBox, height);
    }
}
