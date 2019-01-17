package ca.warp7.rt.ext.views

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureLink
import ca.warp7.rt.core.feature.loadParent
import javafx.scene.Parent
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView


class ViewsFeature : Feature {

    private var preLoaded: Parent? = null

    override val link = FeatureLink("Restructured Tables", "fas-database", 18)

    override fun onOpen(): Pair<Parent?, Parent?> {
        if (preLoaded == null) {
            preLoaded = loadParent("/ca/warp7/rt/ext/views/Table.fxml")
        }

        val rootItem = TreeItem("Tables")
        rootItem.isExpanded = true
        for (i in 1..5) {
            val item = TreeItem("Table $i")
            rootItem.children.add(item)
        }
        val tree = TreeView(rootItem)
        return tree to preLoaded
    }
}