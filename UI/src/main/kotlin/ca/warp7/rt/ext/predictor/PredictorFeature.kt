package ca.warp7.rt.ext.predictor

import ca.warp7.rt.context.api.Feature
import ca.warp7.rt.context.api.FeatureLink
import ca.warp7.rt.core.feature.loadParent
import ca.warp7.rt.ext.scanner.ScannerController
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


