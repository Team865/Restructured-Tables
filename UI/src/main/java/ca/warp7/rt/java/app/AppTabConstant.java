package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.FeatureTabItem;

import java.util.Arrays;
import java.util.List;

class AppTabConstant {
    static List<FeatureTabItem> tabItems = Arrays.asList(

            new FeatureTabItem(),

            new FeatureTabItem("Dataset Options", "fas-cog:18:gray"),
            new FeatureTabItem("External Media", "fas-link:18:gray"),
            new FeatureTabItem("Merge Helper", "fas-code-branch:18:gray"),

            new FeatureTabItem(),

            new FeatureTabItem("Event Overview", "fas-calendar-alt:18:gray"),
            new FeatureTabItem("Scouting Boards", "fas-clipboard:18:gray"),
            new FeatureTabItem("QR Scanner", "fas-camera:18:gray"),
            new FeatureTabItem("Wrong Data", "fas-times:18:gray"),
            new FeatureTabItem("Verification Center", "fas-check:18:gray"),
            new FeatureTabItem("Match Predictor", "fas-balance-scale:18:gray"),
            new FeatureTabItem("Alliance Selection", "fas-list-alt:18:gray"),

            new FeatureTabItem(),

            new FeatureTabItem("[1] raw_data.py", "fab-python:20:gray"),
            new FeatureTabItem("[1] averages.py", "fab-python:20:gray"),
            new FeatureTabItem("[1] auto_list.py", "fab-python:20:gray"),
            new FeatureTabItem("[2] cycle_matrix.py", "fab-python:20:gray"),
            new FeatureTabItem("[2] endgame.py", "fab-python:20:gray"),

            new FeatureTabItem(),

            new FeatureTabItem("Raw Data", "fas-eye:18:gray"),
            new FeatureTabItem("Auto List", "fas-eye:18:gray"),
            new FeatureTabItem("Cycle Matrix", "fas-eye:18:gray"),
            new FeatureTabItem("Endgame", "fas-eye:18:gray"),
            new FeatureTabItem("Team Averages", "fas-eye:18:gray"),

            new FeatureTabItem(),

            new FeatureTabItem("Pivot of Raw Data", "fas-table:18:gray"),

            new FeatureTabItem(),

            new FeatureTabItem("Team Outtakes", "fas-chart-bar:18:gray"),

            new FeatureTabItem()
    );
}
