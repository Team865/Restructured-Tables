package ca.warp7.rt.ext.views

import ca.warp7.rt.core.feature.*
import ca.warp7.rt.core.feature.FeatureItemTab.Group
import javafx.scene.Parent

class ViewsFeature : Feature {

    private val factory = FeatureTabFactory("fas-eye:16:gray",
            featureId, Group.WithFeature)

    private var preLoaded: Parent? = null

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent("/ca/warp7/rt/ext/views/Views.fxml")
        }
        return preLoaded
    }

    override val link = FeatureLink("Table Views", "fas-eye", 16)

    override val loadedTabs
        get() = listOf(
                factory.get("Table Views", "")
        )

    override val featureId get() = "views"
}