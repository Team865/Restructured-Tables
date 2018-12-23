package ca.warp7.rt.ext.media

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab

class MediaFeature : Feature {

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return listOf(FeatureItemTab(
                "External Media", "fas-link:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))
    }

    override fun getFeatureId(): String {
        return "media"
    }
}
