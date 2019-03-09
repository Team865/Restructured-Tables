package ca.warp7.rt.ext.views

import ca.warp7.rt.api.Feature
import ca.warp7.rt.api.FeatureLink
import ca.warp7.rt.api.loadParent
import javafx.scene.Parent


class ViewsFeature : Feature {

    private var preLoaded: Parent? = null

    override val link = FeatureLink("Restructured Tables", "fas-database", 18)

    override fun onOpen(): Pair<Parent?, Parent?> {
//        if (preLoaded == null) {
//            preLoaded = loadParent("/ca/warp7/rt/ext/views/Table.fxml")
//        }
        return null to loadParent("/ca/warp7/rt/ext/views/Table.fxml")//preLoaded
    }
}