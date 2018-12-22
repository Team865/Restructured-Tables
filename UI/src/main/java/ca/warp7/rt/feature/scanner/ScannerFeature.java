package ca.warp7.rt.feature.scanner;

import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.core.feature.FeatureItemTab;
import ca.warp7.rt.core.feature.FeatureTabFactory;
import ca.warp7.rt.core.feature.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.scene.Parent;

import java.util.List;

import static ca.warp7.rt.core.feature.FeatureItemTab.Group;

public class ScannerFeature implements Feature {

    private FeatureTabFactory factory = new FeatureTabFactory("fas-camera:16:gray", getFeatureId(), Group.SingleTab);

    private ScannerController controller;

    private void setController(ScannerController controller) {
        this.controller = controller;
    }

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return FXCollections.singletonObservableList(factory.get("QR Scanner", ""));
    }

    @Override
    public String getFeatureId() {
        return "scanner";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        return FeatureUtils.loadParent("/ca/warp7/rt/feature/scanner/Scanner.fxml", this::setController);
    }

    @Override
    public boolean onCloseRequest() {
        controller.stopCameraStream();
        return true;
    }
}
