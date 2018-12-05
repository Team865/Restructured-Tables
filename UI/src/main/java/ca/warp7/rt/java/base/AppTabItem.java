package ca.warp7.rt.java.base;

public class AppTabItem {
    public String title;
    public String iconLiteral;
    public boolean isSeparator;
    private AppFeature appFeature;

    public AppTabItem(String title, String iconLiteral) {
        this.title = title;
        this.iconLiteral = iconLiteral;
        this.isSeparator = false;
    }

    public AppTabItem(){
        this.isSeparator = true;
        title = "";
        iconLiteral = "";
    }

    public AppFeature getAppFeature() {
        return appFeature;
    }

    public void setAppFeature(AppFeature appFeature) {
        this.appFeature = appFeature;
    }
}
