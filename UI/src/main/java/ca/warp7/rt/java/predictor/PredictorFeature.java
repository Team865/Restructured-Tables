package ca.warp7.rt.java.predictor;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class PredictorFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Match Predictor", "fas-balance-scale:16:gray", getFeatureId(),
                FeatureItemTab.LinkGroup.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "predictor";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        return null;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
