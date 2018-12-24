package ca.warp7.rt.ext.python

import ca.warp7.rt.core.app.setAppStatus
import ca.warp7.rt.core.app.userInputString
import ca.warp7.rt.core.feature.*
import ca.warp7.rt.core.feature.FeatureItemTab.Group
import javafx.scene.Parent

class PythonFeature : Feature {

    private val factory = FeatureTabFactory("fab-python:18:gray",
            featureId, Group.WithFeature)

    private var preLoaded: Parent? = null

    private val actions = listOf(
            factory["Python Scripts", "raw_data.py"]
    )

    private lateinit var controller: PythonController

    private fun setController(controller: PythonController) {
        this.controller = controller
        controller.feature = this
    }

    override val link = FeatureLink("Python Scripts", "fab-python", 18)
    override val loadedTabs get() = actions
    override val featureId get() = "python"

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent<PythonController>("/ca/warp7/rt/ext/python/Python.fxml")
            {
                setController(it)
            }
        }
        controller.setTabItemParams(tab.paramString)
        setAppStatus("Editing python script " + tab.paramString)
        return preLoaded
    }

    fun newScript() {
        val name = userInputString(
                "New Python Script", "Script Name:", "") { s -> s.matches("^[\\w]+$".toRegex()) }
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
        return null to preLoaded
    }
}