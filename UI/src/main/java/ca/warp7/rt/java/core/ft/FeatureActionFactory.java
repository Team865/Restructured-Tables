package ca.warp7.rt.java.core.ft;

public class FeatureActionFactory {
    private String iconLiteral;
    private String featureId;
    private FeatureAction.LinkGroup linkGroup;

    public FeatureActionFactory(String iconLiteral, String featureId, FeatureAction.LinkGroup linkGroup) {
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.linkGroup = linkGroup;
    }

    public FeatureAction get(String actionTitle, FeatureAction.Type type, String paramString) {
        return new FeatureAction(actionTitle, iconLiteral, featureId, linkGroup, type, paramString);
    }
}
