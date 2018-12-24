package ca.warp7.rt.ext.ast

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent

class ASTFeature : Feature {
    override val link = FeatureLink("Alliance Selection", "fas-list-alt", 18)

    override fun onOpen(): Pair<Parent?, Parent?> {
        return null to FeatureUtils.loadParent("/ca/warp7/rt/ext/ast/AST.fxml")
    }
}
