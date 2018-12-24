package ca.warp7.rt.ext.predictor

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink

class PredictorFeature : Feature {
    override val link = FeatureLink("Match Predictor", "fas-balance-scale", 18)
}
