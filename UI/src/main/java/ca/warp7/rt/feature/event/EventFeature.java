package ca.warp7.rt.feature.event;

import ca.warp7.rt.core.ft.Feature;
import ca.warp7.rt.core.ft.FeatureItemTab;

import java.util.Collections;
import java.util.List;

public class EventFeature implements Feature {

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Collections.singletonList(new FeatureItemTab(
                "Event Overview", "fas-trophy:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "event";
    }
}
