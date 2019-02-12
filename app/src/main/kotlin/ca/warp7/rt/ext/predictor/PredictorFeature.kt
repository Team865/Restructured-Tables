package ca.warp7.rt.ext.predictor

import ca.warp7.rt.api.Feature
import ca.warp7.rt.api.FeatureLink
import ca.warp7.rt.api.loadParent
import javafx.scene.Parent


class PredictorFeature : Feature {
    override val link = FeatureLink("Match Predictor", "fas-balance-scale", 18)
    private var controller: PredictorController? = null


    private fun setController(controller: PredictorController) {
        this.controller = controller
    }

    override fun onClose(): Boolean {
        return true
    }

    override fun onOpen(): Pair<Parent?, Parent?> =
            null to loadParent<PredictorController>("/ca/warp7/rt/ext/predictor/predictor.fxml") {
                this.setController(it)
            }
}


