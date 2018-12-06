package ca.warp7.rt.java.core.feature;

@SuppressWarnings({"unused", "WeakerAccess"})
public class FeatureAction {

    public enum Type {
        New, View
    }

    public enum LinkGroup {
        Core, SingleTab, WithFeature
    }

    public String linkTitle;
    public String iconLiteral;
    public String featureId;
    public Type type;
    public String paramString;
    public LinkGroup linkGroup;

    public FeatureAction(String linkTitle,
                         String iconLiteral,
                         String featureId,
                         Type type,
                         String paramString,
                         LinkGroup linkGroup) {
        this.linkTitle = linkTitle;
        this.iconLiteral = iconLiteral;
        this.featureId = featureId;
        this.type = type;
        this.paramString = paramString;
        this.linkGroup = linkGroup;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
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

    public LinkGroup getLinkGroup() {
        return linkGroup;
    }

    public void setLinkGroup(LinkGroup linkGroup) {
        this.linkGroup = linkGroup;
    }
}
