package ca.warp7.rt.java.app;

public class AppInterface {
    static AppController instance;

    public static void setStatusMessage(String statusMessage) {
        if (instance != null) {
            instance.statusMessageLabel.setText(statusMessage);
        }
    }
}
