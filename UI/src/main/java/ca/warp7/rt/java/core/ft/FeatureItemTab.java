package ca.warp7.rt.java.core.ft;

@SuppressWarnings({"unused"})
public class FeatureItemTab {

    public enum LinkGroup {
        Core, SingleTab, WithFeature
    }

    private String actionTitle;
    private String iconLiteral;
    private String featureId;
    private LinkGroup linkGroup;
    private String paramString;

    public FeatureItemTab(String actionTitle,
                          String iconLiteral,
                          String featureId,
                          LinkGroup linkGroup,
                          String paramString) {
        this.actionTitle = actionTitle;
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.linkGroup = linkGroup;
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
