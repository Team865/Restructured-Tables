package ca.warp7.rt.feature.predictor;

import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.core.feature.FeatureItemTab;

import java.util.Collections;
import java.util.List;

public class PredictorFeature implements Feature {
    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Collections.singletonList(new FeatureItemTab(
                "Match Predictor", "fas-balance-scale:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "predictor";
    }
}
