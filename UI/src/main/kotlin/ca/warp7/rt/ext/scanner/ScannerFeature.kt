package ca.warp7.rt.ext.scanner

import ca.warp7.rt.core.feature.*
import ca.warp7.rt.core.feature.FeatureItemTab.Group
import javafx.scene.Parent

class ScannerFeature : Feature {

    private val factory = FeatureTabFactory("fas-camera:16:gray", featureId, Group.SingleTab)

    private var controller: ScannerController? = null

    private fun setController(controller: ScannerController) {
        this.controller = controller
    }

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        return FeatureUtils.loadParent<ScannerController>("/ca/warp7/rt/ext/scanner/Scanner.fxml") {
            this.setController(it)
        }
    }

    override val link = FeatureLink("QR Scanner", "fas-camera", 16)

    override val loadedTabs
        get() = listOf(factory.get("QR Scanner", ""))

    override val featureId get() = "scanner"

    override fun onClose(): Boolean {
        controller!!.stopCameraStream()
        return true
    }

    override fun onOpen(): Pair<Parent?, Parent?> =
            null to FeatureUtils.loadParent<ScannerController>("/ca/warp7/rt/ext/scanner/Scanner.fxml") {
                this.setController(it)
            }
}
