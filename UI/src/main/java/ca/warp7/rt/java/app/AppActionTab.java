package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.FeatureAction;

class AppActionTab {

    static final AppActionTab separator = new AppActionTab(null, true);

    private FeatureAction featureAction;
    private boolean isSeparator;

    private AppActionTab(FeatureAction featureAction, boolean isSeparator) {
        this.featureAction = featureAction;
        this.isSeparator = isSeparator;
    }

    AppActionTab(FeatureAction featureAction) {
        this(featureAction, false);
    }

    FeatureAction getFeatureAction() {
        return featureAction;
    }

    boolean isSeparator() {
        return isSeparator;
    }
}
