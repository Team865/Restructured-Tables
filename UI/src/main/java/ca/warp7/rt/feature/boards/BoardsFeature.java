package ca.warp7.rt.feature.boards;

import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.core.feature.FeatureItemTab;
import javafx.scene.Parent;

import java.util.Collections;
import java.util.List;

public class BoardsFeature implements Feature {

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Collections.singletonList(new FeatureItemTab(
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