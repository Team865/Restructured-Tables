package ca.warp7.rt.java.python;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.base.AppTabItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class PythonFeature implements AppFeature.DocumentBased {
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
        return null;
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {
        tabChangCallback.run();
    }

    @Override
    public void onFeatureInit() {
    }

    @Override
    public ObservableList<AppTabItem> getTabs() {
        return FXCollections.observableArrayList(
                new AppTabItem("[1] raw_data.py", "fab-python:20:gray"),
                new AppTabItem("[1] averages.py", "fab-python:20:gray"),
                new AppTabItem("[1] auto_list.py", "fab-python:20:gray"),
                new AppTabItem("[2] cycle_matrix.py", "fab-python:20:gray"),
                new AppTabItem("[2] endgame.py", "fab-python:20:gray")
        );
    }

    @Override
    public void selectTab(AppTabItem item) {
    }
}
