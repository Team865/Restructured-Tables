package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.AppTabItem;

import java.util.Arrays;
import java.util.List;

public class TabConstant {
    static List<AppTabItem> tabItems = Arrays.asList(

            new AppTabItem(),

            new AppTabItem("Dataset Options", "fas-cog:18:gray"),
            new AppTabItem("External Media", "fas-link:18:gray"),
            new AppTabItem("Merge Helper", "fas-code-branch:18:gray"),

            new AppTabItem(),

            new AppTabItem("Event Overview", "fas-calendar-alt:18:gray"),
            new AppTabItem("Scouting Boards", "fas-clipboard:18:gray"),
            new AppTabItem("QR Scanner", "fas-camera:18:gray"),
            new AppTabItem("Wrong Data", "fas-times:18:gray"),
            new AppTabItem("Verification Center", "fas-check:18:gray"),
            new AppTabItem("Match Predictor", "fas-balance-scale:18:gray"),
            new AppTabItem("Alliance Selection", "fas-list-alt:18:gray"),

            new AppTabItem(),

            new AppTabItem("[1] raw_data.py", "fab-python:20:gray"),
            new AppTabItem("[1] averages.py", "fab-python:20:gray"),
            new AppTabItem("[1] auto_list.py", "fab-python:20:gray"),
            new AppTabItem("[2] cycle_matrix.py", "fab-python:20:gray"),
            new AppTabItem("[2] endgame.py", "fab-python:20:gray"),

            new AppTabItem(),

            new AppTabItem("Raw Data", "fas-eye:18:gray"),
            new AppTabItem("Auto List", "fas-eye:18:gray"),
            new AppTabItem("Cycle Matrix", "fas-eye:18:gray"),
            new AppTabItem("Endgame", "fas-eye:18:gray"),
            new AppTabItem("Team Averages", "fas-eye:18:gray"),

            new AppTabItem(),

            new AppTabItem("Pivot of Raw Data", "fas-table:18:gray"),

            new AppTabItem(),

            new AppTabItem("Team Outtakes", "fas-chart-bar:18:gray"),

            new AppTabItem(),

            new AppTabItem("2018iri_qm36", "fas-video:18:gray"),

            new AppTabItem()
    );
}
