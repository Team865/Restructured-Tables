package ca.warp7.rt.java.python;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.base.AppTabItem;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class PythonFeature implements AppFeature.DocumentBased {
    @Override
    public String getIconLiteral() {
        return null;
    }

    @Override
    public String getFeatureName() {
        return null;
    }

    @Override
    public Parent getViewNodeParent() {
        return null;
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {

    }

    @Override
    public void onFeatureInit() {

    }

    @Override
    public ObservableList<AppTabItem> getTabs() {
        return null;
    }

    @Override
    public void selectTab(AppTabItem item) {

    }
}
