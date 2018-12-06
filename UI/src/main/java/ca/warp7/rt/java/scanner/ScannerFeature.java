package ca.warp7.rt.java.scanner;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.scene.Parent;

public class ScannerFeature implements Feature {
    @Override
    public String getIconLiteral() {
        return "fas-camera:18:gray";
    }

    @Override
    public String getFeatureName() {
        return "QR Scanner";
    }

    @Override
    public Parent getViewParent() {
        return FeatureUtils.getNode("/ca/warp7/rt/stage/scanner/Scanner.fxml", getClass());
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {
        tabChangCallback.run();
    }

    @Override
    public void onFeatureInit() {
    }
}
