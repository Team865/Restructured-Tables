package ca.warp7.rt.feature.scanner

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureItemTab.Group
import ca.warp7.rt.core.feature.FeatureTabFactory
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.collections.FXCollections
import javafx.scene.Parent

class ScannerFeature : Feature {

    private val factory = FeatureTabFactory("fas-camera:16:gray", featureId, Group.SingleTab)

    private var controller: ScannerController? = null

    private fun setController(controller: ScannerController) {
        this.controller = controller
    }

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return FXCollections.singletonObservableList(factory.get("QR Scanner", ""))
    }

    override fun getFeatureId(): String {
        return "scanner"
    }

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        return FeatureUtils.loadParent<ScannerController>("/ca/warp7/rt/feature/scanner/Scanner.fxml") {
            this.setController(it)
        }
    }

    override fun onCloseRequest(): Boolean {
        controller!!.stopCameraStream()
        return true
    }
}
