package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureIcon;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;

class AppElement {

    static HBox tabUIFromAction(AppActionTab tab) {

        FeatureAction action = tab.getFeatureAction();

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
}
