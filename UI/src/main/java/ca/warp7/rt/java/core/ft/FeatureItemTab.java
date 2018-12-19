package ca.warp7.rt.java.core.ft;

@SuppressWarnings({"unused"})
public class FeatureItemTab {

    public enum Group {
        SingleTab, WithFeature
    }

    private String actionTitle;
    private String iconLiteral;
    private String featureId;
    private Group tabGroup;
    private String paramString;

    public FeatureItemTab(String actionTitle,
                          String iconLiteral,
                          String featureId,
                          Group tabGroup,
                          String paramString) {
        this.actionTitle = actionTitle;
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.tabGroup = tabGroup;
        this.paramString = paramString;
    }

    public String getTitle() {
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

    public Group getTabGroup() {
        return tabGroup;
    }

    public void setTabGroup(Group tabGroup) {
        this.tabGroup = tabGroup;
    }
}
