package ca.warp7.rt.java.event;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventFeature implements Feature {

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Event Overview", "fas-trophy:16:gray", getFeatureId(),
                FeatureItemTab.LinkGroup.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "event";
    }
}
