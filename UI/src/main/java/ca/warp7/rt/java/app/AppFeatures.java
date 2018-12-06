package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.python.PythonFeature;
import ca.warp7.rt.java.scanner.ScannerFeature;
import ca.warp7.rt.java.views.ViewsFeature;

import java.util.Arrays;
import java.util.List;

class AppFeatures {
    static final List<Feature> features = Arrays.asList(
            new ScannerFeature(),
            new PythonFeature(),
            new ViewsFeature()
    );
}
