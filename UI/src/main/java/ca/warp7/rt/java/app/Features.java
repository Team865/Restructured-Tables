package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.python.PythonFeature;
import ca.warp7.rt.java.scanner.ScannerFeature;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
class Features {
    static final List<AppFeature> baseFeatures = Collections.emptyList();

    static final List<AppFeature> extendFeatures = Arrays.asList(
            new ScannerFeature()
    );

    static final List<AppFeature.DocumentBased> documentFeatures = Arrays.asList(
            new PythonFeature()
    );

}
