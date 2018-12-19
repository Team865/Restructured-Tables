package ca.warp7.rt.java.predictor;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;

import java.util.List;

public class PredictorFeature implements Feature {
    @Override
    public List<FeatureItemTab> getInitialTabList() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Match Predictor", "fas-balance-scale:16:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "predictor";
    }
}
