package ca.warp7.rt.java.ast;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.scene.Parent;

import java.util.Collections;
import java.util.List;

public class ASTFeature implements Feature {

    @Override
    public List<FeatureItemTab> getLoadedTabs() {
        return Collections.singletonList(new FeatureItemTab(
                "Alliance Selection", "fas-list-alt:18:gray", getFeatureId(),
                FeatureItemTab.Group.SingleTab, ""
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
}
