package ca.warp7.rt.ext.views

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureItemTab.Group
import ca.warp7.rt.core.feature.FeatureTabFactory
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent
import java.util.*

class ViewsFeature : Feature {

    private val factory = FeatureTabFactory("fas-eye:16:gray",
            featureId, Group.WithFeature)

    private var preLoaded: Parent? = null

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return Arrays.asList(
                factory.get("Raw Data", ""),
                factory.get("Auto List", ""),
                factory.get("Endgame", ""),
                factory.get("Team Averages", ""),
                factory.get("Cycle Matrix", "")
        )
    }

    override fun getFeatureId(): String {
        return "views"
    }

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/feature/views/Views.fxml")
        }
        return preLoaded
    }
}