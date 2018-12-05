package ca.warp7.rt.java.views;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.base.AppTabItem;
import ca.warp7.rt.java.base.StageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class ViewsFeature implements AppFeature.MultiTab {
    @Override
    public String getIconLiteral() {
        return "fas-eye:18:gray";
    }

    @Override
    public String getFeatureName() {
        return "Data View";
    }

    @Override
    public Parent getViewParent() {
        return StageUtils.node("/ca/warp7/rt/stage/views/View.fxml", getClass());
    }

    @Override
    public void onRequestTabChange(Runnable tabChangCallback) {
        tabChangCallback.run();
    }

    @Override
    public void onFeatureInit() {
    }

    @Override
    public ObservableList<AppTabItem> getTabs() {
        return FXCollections.observableArrayList(
                new AppTabItem("Raw Data", "fas-eye:18:gray"),
                new AppTabItem("Auto List", "fas-eye:18:gray"),
                new AppTabItem("Cycle Matrix", "fas-eye:18:gray"),
                new AppTabItem("Endgame", "fas-eye:18:gray"),
                new AppTabItem("Team Averages", "fas-eye:18:gray")
        );
    }

    @Override
    public void selectTab(AppTabItem item) {
    }
}
