package ca.warp7.rt.java.views;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureActionFactory;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.ft.FeatureAction.LinkGroup;
import static ca.warp7.rt.java.core.ft.FeatureAction.Type;

public class ViewsFeature implements Feature {

    private FeatureActionFactory factory = new FeatureActionFactory("fas-eye:18:gray", getFeatureId(), LinkGroup.WithFeature);

    private Parent preLoaded;

    private ObservableList<FeatureAction> actions = FXCollections.observableArrayList(
            factory.get("Data View", Type.New, ""),
            factory.get("Raw Data", Type.TabItem, ""),
            factory.get("Auto List", Type.TabItem, ""),
            factory.get("Endgame", Type.TabItem, ""),
            factory.get("Team Averages", Type.TabItem, ""),
            factory.get("Cycle Matrix", Type.TabItem, "")
    );

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return actions;
    }

    @Override
    public String getFeatureId() {
        return "views";
    }

    @Override
    public Parent onAction(Type type, String paramString) {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/stage/views/Views.fxml");
        }
        return preLoaded;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }

}