package ca.warp7.rt.ext.predictor

import ca.warp7.rt.api.Feature
import ca.warp7.rt.api.FeatureLink
import ca.warp7.rt.api.loadParent
import javafx.scene.Parent


class PredictorFeature : Feature {
    override val link = FeatureLink("Match Predictor", "fas-balance-scale", 18)

    override fun onClose(): Boolean {
        return true
    }

    override fun onOpen(): Pair<Parent?, Parent?> =
            null to loadParent("/ca/warp7/rt/ext/predictor/predictor.fxml")
}


