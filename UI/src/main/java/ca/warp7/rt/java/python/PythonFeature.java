package ca.warp7.rt.java.python;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureActionFactory;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.ft.FeatureAction.LinkGroup;
import static ca.warp7.rt.java.core.ft.FeatureAction.Type;

public class PythonFeature implements Feature {

    private FeatureActionFactory factory = new FeatureActionFactory("fab-python:20:gray", getFeatureId(), LinkGroup.WithFeature);

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.observableArrayList(
                factory.get("Python Script", Type.New, ""),
                factory.get("[1] raw_data.py", Type.TabItem, ""),
                factory.get("[1] averages.py", Type.TabItem, ""),
                factory.get("[1] auto_list.py", Type.TabItem, ""),
                factory.get("[2] cycle_matrix.py", Type.TabItem, ""),
                factory.get("[2] endgame.py", Type.TabItem, "")
        );
    }

    @Override
    public String getFeatureId() {
        return "python";
    }

    @Override
    public Parent onAction(Type type, String paramString) {
        return FeatureUtils.loadParent("/ca/warp7/rt/stage/python/PythonScripts.fxml");
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }

}