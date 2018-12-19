package ca.warp7.rt.java.dataset;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatasetFeature implements Feature {
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
}
