package ca.warp7.rt.ext.ast

import ca.warp7.rt.api.Feature
import ca.warp7.rt.api.FeatureLink
import ca.warp7.rt.api.loadParent
import javafx.scene.Parent

class ASTFeature : Feature {
    override val link = FeatureLink("Alliance Selection", "fas-tasks", 18)

    override fun onOpen(): Pair<Parent?, Parent?> {
        return null to loadParent("/ca/warp7/rt/ext/ast/AST.fxml")
    }
}
