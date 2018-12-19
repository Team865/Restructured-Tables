package ca.warp7.rt.java.core.ft;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface Feature {

    default void init() {
    }

    ObservableList<FeatureItemTab> getTabObservable();

    String getFeatureId();

    default Parent onOpenTab(FeatureItemTab tab) {
        return null;
    }

    default boolean onCloseRequest() {
        return true;
    }
}
