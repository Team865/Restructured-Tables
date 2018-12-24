package ca.warp7.rt.ext.python

import ca.warp7.rt.core.app.setAppStatus
import ca.warp7.rt.core.app.userInputString
import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent

class PythonFeature : Feature {

    override val link = FeatureLink("Python Scripts", "fab-python", 20)

    private var preLoaded: Parent? = null
    private lateinit var controller: PythonController

    private fun setController(controller: PythonController) {
        this.controller = controller
        controller.feature = this
    }

    fun newScript() {
        userInputString("New Python Script", "Script Name:", "") {
            it.matches("^[\\w]+$".toRegex())
        }
    }

    override fun setFocused(focused: Boolean) {
        controller.setFocused(focused)
    }

    override fun onOpen(): Pair<Parent?, Parent?> {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent<PythonController>("/ca/warp7/rt/ext/python/Python.fxml")
            {
                setController(it)
            }
        }
        controller.setTabItemParams("documentation.py")
        setAppStatus("Editing python")
        return null to preLoaded
    }
}