package ca.warp7.rt.java.event;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class EventFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(new FeatureAction(
                "Event Overview", "fas-trophy:16:gray", getFeatureId(),
                FeatureAction.LinkGroup.SingleTab, FeatureAction.Type.TabItem, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "event";
    }

    @Override
    public Parent onAction(FeatureAction.Type type, String paramString) {
        return null;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
