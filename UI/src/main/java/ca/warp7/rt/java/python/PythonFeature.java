package ca.warp7.rt.java.python;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureActionFactory;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.ft.FeatureItemTab.LinkGroup;

public class PythonFeature implements Feature {

    private FeatureActionFactory factory = new FeatureActionFactory("fab-python:18:gray",
            getFeatureId(), LinkGroup.WithFeature);

    private Parent preLoaded;

    private ObservableList<FeatureItemTab> actions = FXCollections.observableArrayList(
            factory.get("raw_data", "raw_data.py"),
            factory.get("averages", "averages.py"),
            factory.get("auto_list", "auto_list.py"),
            factory.get("cycle_matrix", "cycle_matrix.py"),
            factory.get("endgame", "endgame.py")
    );

    private PythonController controller;

    private void setController(PythonController controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return actions;
    }

    @Override
    public String getFeatureId() {
        return "python";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/stage/python/Python.fxml", this::setController);
        }
        controller.setTabItemParams(tab.getParamString());
        return preLoaded;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }

}