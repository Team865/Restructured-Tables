package ca.warp7.rt.java.base;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface AppFeature {

    String getIconLiteral();

    String getFeatureName();

    Node getViewNode();

    void onRequestTabChange(Runnable tabChangCallback);

    void onFeatureInit();

    interface SingleBase extends AppFeature {
    }

    interface SingleExtension extends AppFeature {
    }

    interface DocumentBased extends AppFeature {
        ObservableList<AppTabItem> getTabs();

        void selectTab(AppTabItem item);
    }
}
