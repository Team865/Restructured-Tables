package ca.warp7.rt.java.core.ft;

@SuppressWarnings({"unused", "WeakerAccess"})
public class FeatureAction {

    public enum Type {
        New, TabItem
    }

    public enum LinkGroup {
        Core, SingleTab, WithFeature
    }

    private String actionTitle;
    private String iconLiteral;
    private String featureId;
    private LinkGroup linkGroup;
    private Type type;
    private String paramString;

    public FeatureAction(String actionTitle,
                         String iconLiteral,
                         String featureId,
                         LinkGroup linkGroup,
                         Type type,
                         String paramString) {
        this.actionTitle = actionTitle;
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.linkGroup = linkGroup;
        this.type = type;
        this.paramString = paramString;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public String getIconLiteral() {
        return iconLiteral;
    }

    public void setIconLiteral(String iconLiteral) {
        this.iconLiteral = iconLiteral;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getParamString() {
        return paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public LinkGroup getActionGroup() {
        return linkGroup;
    }

    public void setLinkGroup(LinkGroup linkGroup) {
        this.linkGroup = linkGroup;
    }
}
