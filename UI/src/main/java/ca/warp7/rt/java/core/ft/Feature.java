package ca.warp7.rt.java.core.ft;

import javafx.scene.Parent;

import java.util.List;

public interface Feature {

    List<FeatureItemTab> getInitialTabList();

    String getFeatureId();

    default Parent onOpenTab(FeatureItemTab tab) {
        return null;
    }

    default boolean onCloseRequest() {
        return true;
    }
}
