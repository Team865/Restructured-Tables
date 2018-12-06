package ca.warp7.rt.java.scanner;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureAction;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.feature.FeatureAction.*;

public class ScannerFeature implements Feature {

    private Factory factory = new Factory("fas-camera:18:gray", getFeatureId(), LinkGroup.SingleTab);

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(factory.get("QR Scanner", Type.Open, ""));
    }

    @Override
    public String getFeatureId() {
        return "scanner";
    }

    @Override
    public Parent onAction(Type type, String params) {
        return FeatureUtils.getNode("/ca/warp7/rt/stage/scanner/Scanner.fxml", getClass());
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
