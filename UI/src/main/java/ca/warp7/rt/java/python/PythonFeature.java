package ca.warp7.rt.java.python;

import ca.warp7.rt.java.app.AppUtils;
import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureTabFactory;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import java.util.List;

import static ca.warp7.rt.java.core.ft.FeatureItemTab.Group;

public class PythonFeature implements Feature {

    private FeatureTabFactory factory = new FeatureTabFactory("fab-python:18:gray",
            getFeatureId(), Group.WithFeature);

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
    public List<FeatureItemTab> getLoadedTabs() {
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
        AppUtils.setStatusMessage("Editing python script " + tab.getParamString());
        return preLoaded;
    }
}