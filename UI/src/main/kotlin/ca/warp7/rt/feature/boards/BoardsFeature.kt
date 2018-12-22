package ca.warp7.rt.feature.boards

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import javafx.scene.Parent

class BoardsFeature : Feature {

    override fun getLoadedTabs(): List<FeatureItemTab> {
        return listOf(FeatureItemTab(
                "Scouting Boards", "fas-clipboard:18:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))
    }

    override fun getFeatureId(): String {
        return "boards"
    }

    override fun onOpenTab(tab: FeatureItemTab): Parent? {
        return null
    }

    override fun onCloseRequest(): Boolean {
        return true
    }
}
