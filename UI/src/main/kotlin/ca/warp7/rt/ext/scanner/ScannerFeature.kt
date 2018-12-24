package ca.warp7.rt.ext.scanner

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent

class ScannerFeature : Feature {

    override val link = FeatureLink("QR Scanner", "fas-camera", 16)

    private var controller: ScannerController? = null

    private fun setController(controller: ScannerController) {
        this.controller = controller
    }

    override fun onClose(): Boolean {
        controller!!.stopCameraStream()
        return true
    }

    override fun onOpen(): Pair<Parent?, Parent?> =
            null to FeatureUtils.loadParent<ScannerController>("/ca/warp7/rt/ext/scanner/Scanner.fxml") {
                this.setController(it)
            }
}
