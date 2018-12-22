package ca.warp7.rt.core.app;

import ca.warp7.rt.core.feature.FeatureItemTab;
import javafx.scene.Node;
import org.kordamp.ikonli.javafx.FontIcon;

class AppTab {

    private FeatureItemTab featureItemTab;
    private FontIcon icon;
    private Node decorativeNode;
    private int decorativeHeight;

    AppTab(Node decorativeNode, int decorativeHeight) {
        this.decorativeNode = decorativeNode;
        this.decorativeHeight = decorativeHeight;
    }

    AppTab(FeatureItemTab featureItemTab) {
        this.featureItemTab = featureItemTab;
    }

    FeatureItemTab getFeatureItemTab() {
        return featureItemTab;
    }

    boolean isDecorativeNode() {
        return decorativeNode != null;
    }

    Node getDecorativeNode() {
        return decorativeNode;
    }

    FontIcon getIcon() {
        return icon;
    }

    void setIcon(FontIcon icon) {
        this.icon = icon;
    }

    public int getDecorativeHeight() {
        return decorativeHeight;
    }
}
