package ca.warp7.rt.ext.dash

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.loadParent
import javafx.scene.Parent

class DashboardFeature : Feature {

    override val link = FeatureLink("Dashboard", "fas-search", 20)

    override fun onOpen(): Pair<Parent?, Parent?> {
        return loadParent("/ca/warp7/rt/ext/dash/Sidebar.fxml") to loadParent("/ca/warp7/rt/ext/dash/Dashboard.fxml")
    }
}
