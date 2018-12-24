package ca.warp7.rt.ext.dataset

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink

class DatasetFeature : Feature {

    override val link get() = FeatureLink("Dataset Options", "fas-database", 16)

    override val loadedTabs
        get() = listOf(FeatureItemTab(
                "Dataset Options", "fas-database:16:gray", featureId,
                FeatureItemTab.Group.SingleTab, ""
        ))

    override val featureId get() = "dataset"
}
