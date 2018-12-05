package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.base.AppTabItem;
import ca.warp7.rt.java.base.StageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

import java.util.List;

import static ca.warp7.rt.java.app.Features.*;

public class AppTabsController {

    @FXML
    private ListView<AppTabItem> appTabs;

    private ObservableList<AppTabItem> appTabItems = FXCollections.observableArrayList(

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

    private void initTabsItemsAndFactory() {
        appTabs.setItems(appTabItems);
        appTabs.setCellFactory(listView -> new ListCell<AppTabItem>() {
            @Override
            protected void updateItem(AppTabItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (item.isSeparator) {
                    setGraphic(new Separator());
                } else {
                    HBox outer = new HBox();

                    HBox inner = new HBox();
                    inner.setPrefWidth(24);
                    inner.setAlignment(Pos.CENTER);

                    inner.getChildren().add(StageUtils.icon(item.iconLiteral));

                    outer.setSpacing(10);
                    outer.getChildren().add(inner);
                    outer.getChildren().add(new Label(item.title));

                    setGraphic(outer);
                }
            }
        });
    }

    @FXML
    void initialize() {
        initTabsItemsAndFactory();
        appTabItems.clear();
        if (baseFeatures.size() > 0){
            appTabItems.add(new AppTabItem());
            baseFeatures.forEach(AppFeature::onFeatureInit);
            baseFeatures.forEach(feature -> appTabItems.add(fromFeature(feature)));
        }
        if (singleTabFeatures.size() > 0){
            singleTabFeatures.forEach(AppFeature::onFeatureInit);
            appTabItems.add(new AppTabItem());
            singleTabFeatures.forEach(feature -> appTabItems.add(fromFeature(feature)));
        }

        multiTabFeatures.forEach(this::addMultiTab);
        appTabItems.add(new AppTabItem());
    }

    private void  addMultiTab(AppFeature.MultiTab multiTab){
        List<AppTabItem> tabs = multiTab.getTabs();
        if (tabs.size() > 0){
            appTabItems.add(new AppTabItem());
            appTabItems.addAll(tabs);
        }
    }

    private static AppTabItem fromFeature(AppFeature feature) {
        return new AppTabItem(feature.getFeatureName(), feature.getIconLiteral());
    }
}
