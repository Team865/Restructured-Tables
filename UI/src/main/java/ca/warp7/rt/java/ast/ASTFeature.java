package ca.warp7.rt.java.ast;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

public class ASTFeature implements Feature {
    @Override
    public void init() {
    }

    @Override
    public ObservableList<FeatureAction> getActionList() {
        return FXCollections.singletonObservableList(new FeatureAction(
                "Alliance Selection", "fas-list-alt:18:gray", getFeatureId(),
                FeatureAction.LinkGroup.SingleTab, FeatureAction.Type.TabItem, ""
        ));
    }

    @Override
    public String getFeatureId() {
        return "ast";
    }

    @Override
    public Parent onAction(FeatureAction.Type type, String paramString) {
        return FeatureUtils.loadParent("/ca/warp7/rt/stage/ast/AST.fxml");
    }

    @Override
    public boolean onCloseRequest() {
        return true;
    }
}
