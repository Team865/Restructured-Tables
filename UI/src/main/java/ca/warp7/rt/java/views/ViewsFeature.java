package ca.warp7.rt.java.views;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureTabItem;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class ViewsFeature implements Feature.MultiTab {
    @Override
    public String getIconLiteral() {
        return "fas-eye:18:gray";
    }

    @Override
    public String getFeatureName() {
        return "Data View";
    }

    @Override
    public Parent getViewParent() {
        return FeatureUtils.getNode("/ca/warp7/rt/stage/views/View.fxml", getClass());
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {
        tabChangCallback.run();
    }

    @Override
    public void onFeatureInit() {
    }

    @Override
    public ObservableList<FeatureTabItem> getTabs() {
        return FXCollections.observableArrayList(
                new FeatureTabItem("Raw Data", "fas-eye:18:gray"),
                new FeatureTabItem("Auto List", "fas-eye:18:gray"),
                new FeatureTabItem("Cycle Matrix", "fas-eye:18:gray"),
                new FeatureTabItem("Endgame", "fas-eye:18:gray"),
                new FeatureTabItem("Team Averages", "fas-eye:18:gray")
        );
    }

    @Override
    public void selectTab(FeatureTabItem item) {
    }
}
