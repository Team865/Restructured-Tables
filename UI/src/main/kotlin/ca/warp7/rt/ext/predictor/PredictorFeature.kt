package ca.warp7.rt.ext.predictor

import ca.warp7.rt.context.api.Feature
import ca.warp7.rt.context.api.FeatureLink

class PredictorFeature : Feature {
    override val link = FeatureLink("Match Predictor", "fas-balance-scale", 18)
}
