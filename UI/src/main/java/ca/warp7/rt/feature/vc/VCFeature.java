package ca.warp7.rt.feature.vc;

import ca.warp7.rt.core.ft.Feature;
import ca.warp7.rt.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;

import java.util.List;

public class VCFeature implements Feature {

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Verification Center", "fas-check:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "vc";
    }
}
