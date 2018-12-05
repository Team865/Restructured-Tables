package ca.warp7.rt.java.app;

import ca.warp7.rt.java.base.AppFeature;
import ca.warp7.rt.java.base.AppTabItem;
import ca.warp7.rt.java.base.StageController;
import ca.warp7.rt.java.base.StageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

import static ca.warp7.rt.java.app.Features.*;
import static ca.warp7.rt.java.base.StageUtils.stage;

public class AppController implements StageController {

    @FXML
    private ListView<AppTabItem> appTabs;

    private ObservableList<AppTabItem> appTabItems = FXCollections.observableArrayList(TabConstant.tabItems);

    @FXML
    MenuButton newButton;

    @FXML
    Label dataset;

    @FXML
    Label user;

    @FXML
    BorderPane tabContent;

    @Override
    public void setStage(Stage stage) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(true);
                stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE));
            }
        });
    }

    @FXML
    void onEventSelectAction() {
        stage("/ca/warp7/rt/stage/app/EventSelect.fxml", "Select Event", getClass());
    }

    @FXML
    void onSystemStateAction() {
        stage("/ca/warp7/rt/stage/app/SystemState.fxml", "System State", getClass());
    }

    @FXML
    void onPythonScripts() {
        stage("/ca/warp7/rt/stage/python/PythonScripts.fxml", "Python Scripts", getClass());
    }

    @FXML
    void initialize() {
        dataset.setText("2018iri << 865");
        user.setText("Yu Liu");
        initTabsItemsAndFactory();
        addTabs();
        Features.multiTabFeatures.forEach(multiTab -> {
            MenuItem item = new MenuItem();
            item.setText(multiTab.getFeatureName());
            item.setGraphic(StageUtils.icon(multiTab.getIconLiteral()));
            newButton.getItems().add(item);
        });
    }

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
                    HBox hBox = AppUI.tabUIFromItem(item);
                    hBox.setOnMouseClicked(event -> tabContent.setCenter(item.getAppFeature().getViewParent()));
                    setGraphic(hBox);
                }
            }
        });
    }

    private void addTabs() {
        appTabItems.clear();
        if (baseFeatures.size() > 0) {
            appTabItems.add(new AppTabItem());
            baseFeatures.forEach(AppFeature::onFeatureInit);
            baseFeatures.forEach(feature -> appTabItems.add(fromFeature(feature)));
        }
        if (singleTabFeatures.size() > 0) {
            singleTabFeatures.forEach(AppFeature::onFeatureInit);
            appTabItems.add(new AppTabItem());
            singleTabFeatures.forEach(feature -> appTabItems.add(fromFeature(feature)));
        }

        multiTabFeatures.forEach(this::addMultiTab);
        appTabItems.add(new AppTabItem());
    }

    private void addMultiTab(AppFeature.MultiTab multiTab) {
        List<AppTabItem> tabs = multiTab.getTabs();
        if (tabs.size() > 0) {
            appTabItems.add(new AppTabItem());
            tabs.forEach(item -> {
                item.setAppFeature(multiTab);
                appTabItems.add(item);
            });
        }
    }

    private static AppTabItem fromFeature(AppFeature feature) {
        AppTabItem item = new AppTabItem(feature.getFeatureName(), feature.getIconLiteral());
        item.setAppFeature(feature);
        return item;
    }
}
