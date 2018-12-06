package ca.warp7.rt.java.core.feature;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface Feature {

    void onFeatureInit();

    ObservableList<FeatureAction> getActionList();

    String getFeatureId();

    Parent onAction(FeatureAction.Type type, String params);

    boolean onCloseRequest();
}
