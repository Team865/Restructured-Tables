package ca.warp7.rt.java.event;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;

import java.util.List;

public class EventFeature implements Feature {

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Event Overview", "fas-trophy:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "event";
    }
}
