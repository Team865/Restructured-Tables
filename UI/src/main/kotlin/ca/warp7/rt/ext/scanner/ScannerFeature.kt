package ca.warp7.rt.ext.scanner

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.loadParent
import javafx.scene.Parent

class ScannerFeature : Feature {

    override val link = FeatureLink("QR Scanner", "fas-camera", 18)

    private var controller: ScannerController? = null

    private fun setController(controller: ScannerController) {
        this.controller = controller
    }

    override fun onClose(): Boolean {
        controller!!.stopCameraStream()
        return true
    }

    override fun onOpen(): Pair<Parent?, Parent?> =
            null to loadParent<ScannerController>("/ca/warp7/rt/ext/scanner/Scanner.fxml") {
                this.setController(it)
            }
}
