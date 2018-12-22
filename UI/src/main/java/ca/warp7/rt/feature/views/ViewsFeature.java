package ca.warp7.rt.feature.views;

import ca.warp7.rt.core.ft.Feature;
import ca.warp7.rt.core.ft.FeatureItemTab;
import ca.warp7.rt.core.ft.FeatureTabFactory;
import ca.warp7.rt.core.ft.FeatureUtils;
import javafx.scene.Parent;

import java.util.Arrays;
import java.util.List;

import static ca.warp7.rt.core.ft.FeatureItemTab.Group;

public class ViewsFeature implements Feature {

    private FeatureTabFactory factory = new FeatureTabFactory("fas-eye:16:gray",
            getFeatureId(), Group.WithFeature);

    private Parent preLoaded;

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Arrays.asList(
                factory.get("Raw Data", ""),
                factory.get("Auto List", ""),
                factory.get("Endgame", ""),
                factory.get("Team Averages", ""),
                factory.get("Cycle Matrix", "")
        );
    }

    @Override
    public String getFeatureId() {
        return "views";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/feature/views/Views.fxml");
        }
        return preLoaded;
    }
}