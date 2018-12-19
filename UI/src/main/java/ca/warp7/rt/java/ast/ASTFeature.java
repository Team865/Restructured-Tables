package ca.warp7.rt.java.ast;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class ASTFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureItemTab> getTabObservable() {
        return FXCollections.singletonObservableList(new FeatureItemTab(
                "Alliance Selection", "fas-list-alt:18:gray", getFeatureId(),
                FeatureItemTab.LinkGroup.SingleTab, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "ast";
    }

    @Override
    public Parent onOpenTab(FeatureItemTab tab) {
        return FeatureUtils.loadParent("/ca/warp7/rt/stage/ast/AST.fxml");
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
