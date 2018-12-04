package ca.warp7.rt.controller.main;

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
