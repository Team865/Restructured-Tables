package ca.warp7.rt.core.app;

import ca.warp7.rt.core.feature.Feature;
import ca.warp7.rt.ext.ast.ASTFeature;
import ca.warp7.rt.ext.boards.BoardsFeature;
import ca.warp7.rt.ext.dataset.DatasetFeature;
import ca.warp7.rt.ext.event.EventFeature;
import ca.warp7.rt.ext.media.MediaFeature;
import ca.warp7.rt.ext.predictor.PredictorFeature;
import ca.warp7.rt.ext.python.PythonFeature;
import ca.warp7.rt.ext.scanner.ScannerFeature;
import ca.warp7.rt.ext.vc.VCFeature;
import ca.warp7.rt.ext.views.ViewsFeature;

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
