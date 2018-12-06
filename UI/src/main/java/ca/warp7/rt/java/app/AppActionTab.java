package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.FeatureAction;

public class AppActionTab {
    private FeatureAction featureAction;
    private boolean separator;

    public AppActionTab(FeatureAction featureAction, boolean separator) {
        this.featureAction = featureAction;
        this.separator = separator;
    }

    FeatureAction getFeatureAction() {
        return featureAction;
    }

    boolean isSeparator() {
        return separator;
    }
}
