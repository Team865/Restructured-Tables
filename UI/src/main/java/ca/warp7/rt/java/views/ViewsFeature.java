package ca.warp7.rt.java.views;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureActionFactory;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import static ca.warp7.rt.java.core.ft.FeatureItemTab.Group;

public class ViewsFeature implements Feature {

    private FeatureActionFactory factory = new FeatureActionFactory("fas-eye:16:gray",
            getFeatureId(), Group.WithFeature);

    private Parent preLoaded;

    private ObservableList<FeatureItemTab> actions = FXCollections.observableArrayList(
            factory.get("Raw Data", ""),
            factory.get("Auto List", ""),
            factory.get("Endgame", ""),
            factory.get("Team Averages", ""),
            factory.get("Cycle Matrix", "")
    );

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return actions;
    }

    @Override
    public String getFeatureId() {
        return "views";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/stage/views/Views.fxml");
        }
        return preLoaded;
    }
}