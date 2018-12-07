package ca.warp7.rt.java.dataset;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class DatasetFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(new FeatureAction(
                "Dataset Options", "fas-cog:18:gray", getFeatureId(),
                FeatureAction.LinkGroup.Core, FeatureAction.Type.TabItem, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "dataset";
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
