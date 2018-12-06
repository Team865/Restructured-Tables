package ca.warp7.rt.java.core.ft;

import javafx.collections.ObservableList;
import javafx.scene.Parent;

public interface Feature {

    void init();

    ObservableList<FeatureAction> getActionList();

    String getFeatureId();

    Parent onAction(FeatureAction.Type type, String paramString);

    boolean onCloseRequest();
}
