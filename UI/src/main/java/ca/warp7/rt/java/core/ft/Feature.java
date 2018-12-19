package ca.warp7.rt.java.core.ft;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface Feature {

    void init();

    ObservableList<FeatureItemTab> getTabObservable();

    String getFeatureId();

    Parent onOpenTab(FeatureItemTab tab);

    boolean onCloseRequest();
}
