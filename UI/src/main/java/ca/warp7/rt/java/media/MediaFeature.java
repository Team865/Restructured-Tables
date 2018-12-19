package ca.warp7.rt.java.media;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;

import java.util.List;

public class MediaFeature implements Feature {

    @Override
    public List<FeatureItemTab> getInitialTabList() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "External Media", "fas-link:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "media";
    }
}
