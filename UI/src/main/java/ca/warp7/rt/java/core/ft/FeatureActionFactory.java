package ca.warp7.rt.java.core.ft;

public class FeatureActionFactory {
    private String iconLiteral;
    private String featureId;
    private FeatureItemTab.LinkGroup linkGroup;

    public FeatureActionFactory(String iconLiteral, String featureId, FeatureItemTab.LinkGroup linkGroup) {
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.linkGroup = linkGroup;
    }

    public FeatureItemTab get(String actionTitle, String paramString) {
        return new FeatureItemTab(actionTitle, iconLiteral, featureId, linkGroup, paramString);
    }
}
