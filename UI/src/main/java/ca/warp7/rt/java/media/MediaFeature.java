package ca.warp7.rt.java.media;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MediaFeature implements Feature {

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "External Media", "fas-link:16:gray", getFeatureId(),
                FeatureItemTab.LinkGroup.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "media";
    }
}
