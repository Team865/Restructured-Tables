package ca.warp7.rt.feature.ast;

import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.core.feature.FeatureItemTab;
import ca.warp7.rt.core.feature.FeatureUtils;
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
        return FeatureUtils.loadParent("/ca/warp7/rt/feature/ast/AST.fxml");
    }
}
