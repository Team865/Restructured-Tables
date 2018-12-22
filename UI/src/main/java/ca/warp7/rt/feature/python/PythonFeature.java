package ca.warp7.rt.feature.python;

import ca.warp7.rt.core.app.AppUtils;
import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.core.feature.FeatureItemTab;
import ca.warp7.rt.core.feature.FeatureTabFactory;
import ca.warp7.rt.core.feature.FeatureUtils;
import javafx.scene.Parent;

import java.util.Arrays;
import java.util.List;

import static ca.warp7.rt.core.feature.FeatureItemTab.Group;

public class PythonFeature implements Feature {

    private FeatureTabFactory factory = new FeatureTabFactory("fab-python:18:gray",
            getFeatureId(), Group.WithFeature);

    private Parent preLoaded;

    private List<FeatureItemTab> actions = Arrays.asList(
            factory.get("raw_data", "raw_data.py"),
            factory.get("averages", "averages.py"),
            factory.get("auto_list", "auto_list.py"),
            factory.get("cycle_matrix", "cycle_matrix.py"),
            factory.get("endgame", "endgame.py")
    );

    private PythonController controller;

    private void setController(PythonController controller) {
        this.controller = controller;
        controller.feature = this;
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
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/feature/python/Python.fxml", this::setController);
        }
        controller.setTabItemParams(tab.getParamString());
        AppUtils.setStatusMessage("Editing python script " + tab.getParamString());
        return preLoaded;
    }

    void newScript() {
        String name = AppUtils.getString(
                "New Python Script", "Script Name:", "", s -> s.matches("^[\\w]+$"));

        if (name != null) AppUtils.insertTab(factory.get(name, name + ".py"));
    }

    @Override
    public void setFocused(boolean focused) {
        controller.setFocused(focused);
    }
}