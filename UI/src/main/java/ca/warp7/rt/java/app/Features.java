package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.python.PythonFeature;
import ca.warp7.rt.java.scanner.ScannerFeature;
import ca.warp7.rt.java.views.ViewsFeature;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
class Features {
    static final List<AppFeature> baseFeatures = Collections.emptyList();

    static final List<AppFeature> singleTabFeatures = Arrays.asList(
            new ScannerFeature()
    );

    static final List<AppFeature.MultiTab> multiTabFeatures = Arrays.asList(
            new PythonFeature(),
            new ViewsFeature()
    );
}
