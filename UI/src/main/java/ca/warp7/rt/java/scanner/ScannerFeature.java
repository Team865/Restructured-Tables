package ca.warp7.rt.java.scanner;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.base.StageUtils;
import javafx.scene.Parent;

public class ScannerFeature implements AppFeature {
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
        return StageUtils.node("/ca/warp7/rt/stage/scanner/Scanner.fxml", getClass());
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {
        tabChangCallback.run();
    }

    @Override
    public void onFeatureInit() {
    }
}
