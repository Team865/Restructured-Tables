package ca.warp7.rt.java.core.ft;

@Deprecated
public class FeatureTabItem {
    public String title;
    public String iconLiteral;
    public boolean isSeparator;
    private Feature feature;

    public FeatureTabItem(String title, String iconLiteral) {
        this.title = title;
        this.iconLiteral = iconLiteral;
        this.isSeparator = false;
    }

    public FeatureTabItem() {
        this.isSeparator = true;
        title = "";
        iconLiteral = "";
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
