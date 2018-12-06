package ca.warp7.rt.java.views;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.ft.FeatureAction.*;

public class ViewsFeature implements Feature {

    private Factory factory = new Factory("fas-eye:18:gray", getFeatureId(), LinkGroup.SingleTab);

    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.observableArrayList(
                factory.get("Data View", Type.New, ""),
                factory.get("Raw Data", Type.TabItem, ""),
                factory.get("Auto List", Type.TabItem, ""),
                factory.get("Endgame", Type.TabItem, ""),
                factory.get("Team Averages", Type.TabItem, ""),
                factory.get("Cycle Matrix", Type.TabItem, "")
        );
    }

    @Override
    public String getFeatureId() {
        return "views";
    }

    @Override
    public Parent onAction(Type type, String paramString) {
        return FeatureUtils.loadParent("/ca/warp7/rt/stage/views/View.fxml", getClass());
    }

    @Override
    public boolean onCloseRequest() {
        return false;
    }

}