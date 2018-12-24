package ca.warp7.rt.ext.vc

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink

class VCFeature : Feature {

    override val link = FeatureLink("Verification Center", "fas-check", 16)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "Verification Center", "fas-check:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "vc"
}
