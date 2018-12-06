package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.python.PythonFeature;
import ca.warp7.rt.java.scanner.ScannerFeature;
import ca.warp7.rt.java.views.ViewsFeature;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
class AppFeatures {
    static final List<Feature> baseFeatures = Collections.emptyList();

    static final List<Feature> singleTabFeatures = Arrays.asList(
            new ScannerFeature()
    );

    static final List<Feature.MultiTab> multiTabFeatures = Arrays.asList(
            new PythonFeature(),
            new ViewsFeature()
    );
}
