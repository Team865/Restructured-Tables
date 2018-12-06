package ca.warp7.rt.java.scanner;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureActionFactory;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.ft.FeatureAction.LinkGroup;
import static ca.warp7.rt.java.core.ft.FeatureAction.Type;

public class ScannerFeature implements Feature {

    private FeatureActionFactory factory = new FeatureActionFactory("fas-camera:18:gray", getFeatureId(), LinkGroup.WithFeature);

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(factory.get("QR Scanner", Type.TabItem, ""));
    }

    @Override
    public String getFeatureId() {
        return "scanner";
    }

    @Override
    public Parent onAction(Type type, String paramString) {
        return FeatureUtils.loadParent("/ca/warp7/rt/stage/scanner/Scanner.fxml", getClass());
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
