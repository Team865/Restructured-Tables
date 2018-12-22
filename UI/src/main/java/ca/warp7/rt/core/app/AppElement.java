package ca.warp7.rt.core.app;

import ca.warp7.rt.core.feature.FeatureIcon;
import ca.warp7.rt.core.feature.FeatureItemTab;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

class AppElement {

    static HBox tabUIFromAction(AppTab tab) {

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
        outer.getChildren().add(new Label(action.getTitle()));

        return outer;
    }

    static void showMemoryAlert() {
        String mem = String.format("Memory: %.2f MB", (Runtime.getRuntime().totalMemory()
                - Runtime.getRuntime().freeMemory()) / 1000000.0);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mem, ButtonType.OK);
        new Thread(System::gc).start();
        alert.showAndWait();
    }

    static AppTab getTeamLogo() {
        int height = 72;
        ImageView teamLogo = new ImageView();
        teamLogo.setImage(new Image("/ca/warp7/rt/res/warp7.png"));
        teamLogo.setPreserveRatio(true);
        teamLogo.setFitHeight(height);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.getChildren().add(teamLogo);
        return new AppTab(hBox, height);
    }

    static String getTitle(FeatureItemTab tab) {
        String title;
        if (tab.getTabGroup() == FeatureItemTab.Group.SingleTab) title = String.format("%s", tab.getTitle());
        else {
            String id = tab.getFeatureId();
            String capId = id.substring(0, 1).toUpperCase() + id.substring(1);
            title = String.format("%s - %s", tab.getTitle(), capId);
        }
        return title;
    }
}