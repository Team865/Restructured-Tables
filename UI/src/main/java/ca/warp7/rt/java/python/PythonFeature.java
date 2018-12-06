package ca.warp7.rt.java.python;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureAction;
import ca.warp7.rt.java.core.feature.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.feature.FeatureAction.*;

public class PythonFeature implements Feature {

    private Factory factory = new Factory("fab-python:20:gray", getFeatureId(), LinkGroup.SingleTab);

    @Override
    public void onFeatureInit() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.observableArrayList(
                factory.get("Python Script", Type.New, ""),
                factory.get("[1] raw_data.py", Type.Open, ""),
                factory.get("[1] averages.py", Type.Open, ""),
                factory.get("[1] auto_list.py", Type.Open, ""),
                factory.get("[2] cycle_matrix.py", Type.Open, ""),
                factory.get("[2] endgame.py", Type.Open, "")
        );
    }

    @Override
    public String getFeatureId() {
        return "python";
    }

    @Override
    public Parent onAction(Type type, String params) {
        return FeatureUtils.getNode("/ca/warp7/rt/stage/python/PythonScripts.fxml", getClass());
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }

}