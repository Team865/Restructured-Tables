package ca.warp7.rt.core.ft;

public class FeatureTabFactory {
    private String iconLiteral;
    private String featureId;
    private FeatureItemTab.Group group;

    public FeatureTabFactory(String iconLiteral, String featureId, FeatureItemTab.Group group) {
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.group = group;
    }

    public FeatureItemTab get(String actionTitle, String paramString) {
        return new FeatureItemTab(actionTitle, iconLiteral, featureId, group, paramString);
    }
}
