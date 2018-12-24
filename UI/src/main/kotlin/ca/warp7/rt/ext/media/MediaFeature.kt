package ca.warp7.rt.ext.media

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink

class MediaFeature : Feature {
    override val link = FeatureLink("External Media", "fas-link", 16)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "External Media", "fas-link:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "dataset"
}
