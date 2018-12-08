package ca.warp7.rt.java.vc;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class VCFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(new FeatureAction(
                "Verification Center", "fas-check:18:gray", getFeatureId(),
                FeatureAction.LinkGroup.SingleTab, FeatureAction.Type.TabItem, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "vc";
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
