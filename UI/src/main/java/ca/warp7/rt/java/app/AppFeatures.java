package ca.warp7.rt.java.app;

import ca.warp7.rt.java.ast.ASTFeature;
import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.python.PythonFeature;
import ca.warp7.rt.java.scanner.ScannerFeature;
import ca.warp7.rt.java.views.ViewsFeature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AppFeatures {
    static final List<Feature> features = Arrays.asList(
            new ScannerFeature(),
            new ASTFeature(),
            new PythonFeature(),
            new ViewsFeature()
    );

    static final Map<String, Feature> featureMap = new HashMap<>();

    static {
        for (Feature feature : features) {
            featureMap.put(feature.getFeatureId(), feature);
        }
    }
}
