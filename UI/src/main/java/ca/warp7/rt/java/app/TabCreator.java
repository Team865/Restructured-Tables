package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.AppTabItem;
import ca.warp7.rt.java.base.StageUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TabCreator {
    static HBox getTab(AppTabItem item) {
        HBox outer = new HBox();

        HBox inner = new HBox();
        inner.setPrefWidth(24);
        inner.setAlignment(Pos.CENTER);

        inner.getChildren().add(StageUtils.icon(item.iconLiteral));

        outer.setSpacing(10);
        outer.getChildren().add(inner);
        outer.getChildren().add(new Label(item.title));

        return outer;
    }
}
