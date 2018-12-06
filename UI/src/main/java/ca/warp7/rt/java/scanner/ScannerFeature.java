package ca.warp7.rt.java.scanner;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureAction;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class ScannerFeature implements Feature {

    @Override
    public void onFeatureInit() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList();
    }

    @Override
    public String getFeatureId() {
        return "scanner";
    }

    @Override
    public Parent onAction(FeatureAction.Type type, String params) {
        return FeatureUtils.getNode("/ca/warp7/rt/stage/scanner/Scanner.fxml", getClass());
    }

    @Override
    public boolean onCloseRequest() {
        return false;
    }
}
