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

    private FeatureActionFactory factory = new FeatureActionFactory("fab-python:18:gray",
            getFeatureId(), LinkGroup.WithFeature);

    private Parent preLoaded;

    private ObservableList<FeatureAction> actions = FXCollections.observableArrayList(
            factory.get("Python Script", Type.New, ""),
            factory.get("[1] raw_data.py", Type.TabItem, "raw_data.py"),
            factory.get("[1] averages.py", Type.TabItem, "averages.py"),
            factory.get("[1] auto_list.py", Type.TabItem, "auto_list.py"),
            factory.get("[2] cycle_matrix.py", Type.TabItem, "cycle_matrix.py"),
            factory.get("[2] endgame.py", Type.TabItem, "endgame.py")
    );

    private PythonController controller;

    private void setController(PythonController controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return actions;
    }

    @Override
    public String getFeatureId() {
        return "python";
    }

    @Override
    public Parent onAction(Type type, String paramString) {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/stage/python/Python.fxml", this::setController);
        }
        controller.setTabItemParams(paramString);
        return preLoaded;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }

}