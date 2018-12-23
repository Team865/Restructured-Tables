package ca.warp7.rt.ext.dataset

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab

class DatasetFeature : Feature {
    override fun getLoadedTabs(): List<FeatureItemTab> {
        return listOf(FeatureItemTab(
                "Dataset Options", "fas-database:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))
    }

    override fun getFeatureId(): String {
        return "dataset"
    }
}
