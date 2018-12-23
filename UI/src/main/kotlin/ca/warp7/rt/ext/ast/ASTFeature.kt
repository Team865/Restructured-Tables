package ca.warp7.rt.ext.ast

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent

class ASTFeature : Feature {

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return listOf(FeatureItemTab(
                "Alliance Selection", "fas-list-alt:18:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))
    }

    override fun getFeatureId(): String {
        return "ast"
    }

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        return FeatureUtils.loadParent("/ca/warp7/rt/ext/ast/AST.fxml")
    }
}
