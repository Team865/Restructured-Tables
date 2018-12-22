package ca.warp7.rt.core.app;

import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.feature.ast.ASTFeature;
import ca.warp7.rt.feature.boards.BoardsFeature;
import ca.warp7.rt.feature.dataset.DatasetFeature;
import ca.warp7.rt.feature.event.EventFeature;
import ca.warp7.rt.feature.media.MediaFeature;
import ca.warp7.rt.feature.predictor.PredictorFeature;
import ca.warp7.rt.feature.python.PythonFeature;
import ca.warp7.rt.feature.scanner.ScannerFeature;
import ca.warp7.rt.feature.vc.VCFeature;
import ca.warp7.rt.feature.views.ViewsFeature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AppFeatures {
    static final List<Feature> features = Arrays.asList(
            new DatasetFeature(),
            new EventFeature(),
            new ASTFeature(),
            new PredictorFeature(),
            new MediaFeature(),
            new BoardsFeature(),
            new ScannerFeature(),
            new VCFeature(),
            new PythonFeature(),
            new ViewsFeature()
    );

    static final Map<String, Feature> featureMap = new HashMap<>();

    static {
        features.forEach(feature -> featureMap.put(feature.getFeatureId(), feature));
    }
}
