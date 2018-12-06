package ca.warp7.rt.java.python;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureTabItem;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class PythonFeature implements Feature {
    @Override
    public String getIconLiteral() {
        return "fab-python:20:gray";
    }

    @Override
    public String getFeatureName() {
        return "Python Script";
    }

    @Override
    public Parent getViewParent() {
        return FeatureUtils.getNode("/ca/warp7/rt/stage/python/PythonScripts.fxml", getClass());
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {
        tabChangCallback.run();
    }

    @Override
    public void onFeatureInit() {
    }

    @Override
    public ObservableList<FeatureTabItem> getTabs() {
        return FXCollections.observableArrayList(
                new FeatureTabItem("[1] raw_data.py", "fab-python:20:gray"),
                new FeatureTabItem("[1] averages.py", "fab-python:20:gray"),
                new FeatureTabItem("[1] auto_list.py", "fab-python:20:gray"),
                new FeatureTabItem("[2] cycle_matrix.py", "fab-python:20:gray"),
                new FeatureTabItem("[2] endgame.py", "fab-python:20:gray")
        );
    }

    @Override
    public void selectTab(FeatureTabItem item) {
    }
}
