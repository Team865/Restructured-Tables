package ca.warp7.rt.ext.ast

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent

class ASTFeature : Feature {
    override val link get() = FeatureLink("Alliance Selection", "fas-list-alt", 18)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "Alliance Selection", "fas-list-alt:18:gray", this.featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "ast"

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        return FeatureUtils.loadParent("/ca/warp7/rt/ext/ast/AST.fxml")
    }
}
