package ca.warp7.rt.java.media;

import ca.warp7.rt.core.ft.Feature;
import ca.warp7.rt.core.ft.FeatureItemTab;

import java.util.Collections;
import java.util.List;

public class MediaFeature implements Feature {

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Collections.singletonList(new FeatureItemTab(
                "External Media", "fas-link:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "media";
    }
}
