package ca.warp7.rt.ext.dash

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.loadParent
import javafx.scene.Parent

class DashboardFeature : Feature {

    override val link = FeatureLink("Context Dashboard", "fas-code-branch", 20)

    override fun onOpen(): Pair<Parent?, Parent?> {
        return null to loadParent("/ca/warp7/rt/ext/dash/Dashboard.fxml")
    }
}
