package ca.warp7.rt.java.dataset;

import ca.warp7.rt.core.ft.Feature;
import ca.warp7.rt.core.ft.FeatureItemTab;

import java.util.Collections;
import java.util.List;

public class DatasetFeature implements Feature {
    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Collections.singletonList(new FeatureItemTab(
                "Dataset Options", "fas-database:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "dataset";
    }
}
