package ca.warp7.rt.java.base;

class AppTabItem {
    String title;
    String iconLiteral;
    boolean isSeparator;

    AppTabItem(String title, String iconLiteral) {
        this.title = title;
        this.iconLiteral = iconLiteral;
        this.isSeparator = false;
    }

    AppTabItem(){
        this.isSeparator = true;
        title = "";
        iconLiteral = "";
    }
}
