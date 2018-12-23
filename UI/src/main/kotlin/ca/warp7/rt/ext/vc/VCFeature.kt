package ca.warp7.rt.ext.vc

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab

class VCFeature : Feature {

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return listOf(FeatureItemTab(
                "Verification Center", "fas-check:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))
    }

    override fun getFeatureId(): String {
        return "vc"
    }
}
