package ca.warp7.rt.core.app

import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.ext.ast.ASTFeature
import ca.warp7.rt.ext.boards.BoardsFeature
import ca.warp7.rt.ext.dataset.DatasetFeature
import ca.warp7.rt.ext.event.EventFeature
import ca.warp7.rt.ext.media.MediaFeature
import ca.warp7.rt.ext.predictor.PredictorFeature
import ca.warp7.rt.ext.python.PythonFeature
import ca.warp7.rt.ext.scanner.ScannerFeature
import ca.warp7.rt.ext.vc.VCFeature
import ca.warp7.rt.ext.views.ViewsFeature
import java.util.*

internal object AppFeatures {
    val features = listOf(
            DatasetFeature(),
            EventFeature(),
            ASTFeature(),
            PredictorFeature(),
            MediaFeature(),
            BoardsFeature(),
            ScannerFeature(),
            VCFeature(),
            PythonFeature(),
            ViewsFeature()
    )

    val featureMap: MutableMap<String, Feature> = HashMap()

    init {
        features.forEach { feature -> featureMap[feature.featureId] = feature }
    }
}
