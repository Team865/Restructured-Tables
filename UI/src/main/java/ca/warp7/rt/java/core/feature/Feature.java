package ca.warp7.rt.java.core.feature;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface Feature {

    String getIconLiteral();

    String getFeatureName();

    Parent getViewParent();

    void onRequestTabChange(Runnable tabChangCallback);

    void onFeatureInit();

    interface MultiTab extends Feature {
        ObservableList<FeatureTabItem> getTabs();

        void selectTab(FeatureTabItem item);
    }
}
