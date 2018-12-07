package ca.warp7.rt.java.media;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class MediaFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(new FeatureAction(
                "External Media", "fas-link:18:gray", getFeatureId(),
                FeatureAction.LinkGroup.SingleTab, FeatureAction.Type.TabItem, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "media";
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
