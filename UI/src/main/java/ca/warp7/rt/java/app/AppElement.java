package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.FeatureAction;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

class AppElement {

    static HBox tabUIFromAction(FeatureAction action) {
        HBox outer = new HBox();

        HBox inner = new HBox();
        inner.setPrefWidth(24);
        inner.setAlignment(Pos.CENTER);

        inner.getChildren().add(FeatureUtils.getIcon(action.getIconLiteral()));

        outer.setSpacing(10);
        outer.getChildren().add(inner);
        outer.getChildren().add(new Label(action.getActionTitle()));

        return outer;
    }
}
