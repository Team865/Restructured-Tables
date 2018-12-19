package ca.warp7.rt.java.boards;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import javafx.collections.FXCollections;
import javafx.scene.Parent;

import java.util.List;

public class BoardsFeature implements Feature {

    @Override
    public List<FeatureItemTab> getInitialTabList() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Scouting Boards", "fas-clipboard:18:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "boards";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        return null;
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
