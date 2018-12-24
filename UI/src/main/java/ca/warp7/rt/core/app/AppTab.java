package ca.warp7.rt.core.app;

import ca.warp7.rt.core.feature.FeatureItemTab;
import org.kordamp.ikonli.javafx.FontIcon;

class AppTab {
    private FeatureItemTab featureItemTab;
    private FontIcon icon;

    AppTab(FeatureItemTab featureItemTab) {
        this.featureItemTab = featureItemTab;
    }

    FeatureItemTab getFeatureItemTab() {
        return featureItemTab;
    }

    FontIcon getIcon() {
        return icon;
    }

    void setIcon(FontIcon icon) {
        this.icon = icon;
    }
}
