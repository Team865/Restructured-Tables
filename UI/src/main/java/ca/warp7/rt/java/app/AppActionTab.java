package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.FeatureItemTab;
import org.kordamp.ikonli.javafx.FontIcon;

class AppActionTab {

    static final AppActionTab separator = new AppActionTab(null, true);

    private FeatureItemTab featureItemTab;
    private boolean isSeparator;
    private FontIcon icon;

    private AppActionTab(FeatureItemTab featureItemTab, boolean isSeparator) {
        this.featureItemTab = featureItemTab;
        this.isSeparator = isSeparator;
    }

    AppActionTab(FeatureItemTab featureItemTab) {
        this(featureItemTab, false);
    }

    FeatureItemTab getFeatureItemTab() {
        return featureItemTab;
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
