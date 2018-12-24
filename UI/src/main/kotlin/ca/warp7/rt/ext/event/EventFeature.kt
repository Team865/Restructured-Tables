package ca.warp7.rt.ext.event

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink

class EventFeature : Feature {
    override val link get() = FeatureLink("Event Overview", "fas-trophy", 16)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "Event Overview", "fas-trophy:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "dataset"
}
