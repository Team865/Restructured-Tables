package ca.warp7.rt.ext.boards

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink

class BoardsFeature : Feature {
    override val link = FeatureLink("Scouting Boards", "fas-clipboard", 18)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "Scouting Boards", "fas-clipboard:18:gray", this.featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "boards"
}
