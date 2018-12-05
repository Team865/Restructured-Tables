package ca.warp7.rt.java.base;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface AppFeature {

    String getIconLiteral();

    String getFeatureName();

    Parent getViewParent();

    void onRequestTabChange(Runnable tabChangCallback);

    void onFeatureInit();

    interface MultiTab extends AppFeature {
        ObservableList<AppTabItem> getTabs();

        void selectTab(AppTabItem item);
    }
}
