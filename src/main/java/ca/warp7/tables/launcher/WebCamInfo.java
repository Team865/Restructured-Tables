package ca.warp7.tables.launcher;

class WebCamInfo {

    private String webCamName;
    private int webCamIndex;

    String getWebCamName() {
        return webCamName;
    }

    void setWebCamName(String webCamName) {
        this.webCamName = webCamName;
    }

    int getWebCamIndex() {
        return webCamIndex;
    }

    void setWebCamIndex(int webCamIndex) {
        this.webCamIndex = webCamIndex;
    }

    @Override
    public String toString() {
        return webCamName;
    }
}
