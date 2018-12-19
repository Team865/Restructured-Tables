package ca.warp7.rt.java.dataset;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class DatasetFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Dataset Options", "fas-database:16:gray", getFeatureId(),
                FeatureItemTab.LinkGroup.Core, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "dataset";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        return null;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
