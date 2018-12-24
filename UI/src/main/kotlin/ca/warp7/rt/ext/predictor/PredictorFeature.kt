package ca.warp7.rt.ext.predictor

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink

class PredictorFeature : Feature {
    override val link = FeatureLink("Match Predictor", "fas-balance-scale", 16)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "Match Predictor", "fas-balance-scale:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "predictor"
}
