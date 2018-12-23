package ca.warp7.rt.ext.python

import ca.warp7.rt.core.app.AppUtils
import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureItemTab.Group
import ca.warp7.rt.core.feature.FeatureTabFactory
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.scene.Parent
import java.util.*

class PythonFeature : Feature {

    private val factory = FeatureTabFactory("fab-python:18:gray",
            featureId, Group.WithFeature)

    private var preLoaded: Parent? = null

    private val actions = Arrays.asList(
            factory["raw_data", "raw_data.py"],
            factory["averages", "averages.py"],
            factory["auto_list", "auto_list.py"],
            factory["cycle_matrix", "cycle_matrix.py"],
            factory["endgame", "endgame.py"]
    )

    private lateinit var controller: PythonController

    private fun setController(controller: PythonController) {
        this.controller = controller
        controller.feature = this
    }

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return actions
    }

    override fun getFeatureId(): String {
        return "python"
    }

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        if (preLoaded == null) {
            preLoaded = FeatureUtils.loadParent<PythonController>("/ca/warp7/rt/feature/python/Python.fxml")
            {
                setController(it)
            }
        }
        controller.setTabItemParams(tab.paramString)
        AppUtils.setStatusMessage("Editing python script " + tab.paramString)
        return preLoaded
    }

    fun newScript() {
        val name = AppUtils.getString(
                "New Python Script", "Script Name:", "") { s -> s.matches("^[\\w]+$".toRegex()) }

        if (name != null) AppUtils.insertTab(factory.get(name, "$name.py"))
    }

    override fun setFocused(focused: Boolean) {
        controller.setFocused(focused)
    }
}