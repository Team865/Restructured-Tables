package ca.warp7.rt.java.core.ft;

public class FeatureActionFactory {
    private String iconLiteral;
    private String featureId;
    private FeatureItemTab.Group group;

    public FeatureActionFactory(String iconLiteral, String featureId, FeatureItemTab.Group group) {
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.group = group;
    }

    public FeatureItemTab get(String actionTitle, String paramString) {
        return new FeatureItemTab(actionTitle, iconLiteral, featureId, group, paramString);
    }
}
