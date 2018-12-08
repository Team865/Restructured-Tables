package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.FeatureAction;
import org.kordamp.ikonli.javafx.FontIcon;

class AppActionTab {

    static final AppActionTab separator = new AppActionTab(null, true);

    private FeatureAction featureAction;
    private boolean isSeparator;
    private FontIcon icon;

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

    FontIcon getIcon() {
        return icon;
    }

    void setIcon(FontIcon icon) {
        this.icon = icon;
    }
}
