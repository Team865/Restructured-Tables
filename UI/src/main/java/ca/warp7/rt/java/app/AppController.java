package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.Feature;
import ca.warp7.rt.java.core.feature.FeatureStage;
import ca.warp7.rt.java.core.feature.FeatureTabItem;
import ca.warp7.rt.java.core.feature.FeatureUtils;
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

import static ca.warp7.rt.java.app.AppFeatures.*;
import static ca.warp7.rt.java.core.feature.FeatureUtils.showStage;

public class AppController implements FeatureStage {

    @FXML
    MenuButton newButton;
    @FXML
    Label dataset;
    @FXML
    Label user;
    @FXML
    BorderPane tabContent;
    @FXML
    HBox tabsAndContent;
    @FXML
    CheckBox hideSidebarCheckbox;
    @FXML
    private ListView<FeatureTabItem> appTabs;
    private ObservableList<FeatureTabItem> featureTabItems = FXCollections.observableArrayList(AppTabConstant.tabItems);

    private static FeatureTabItem fromFeature(Feature feature) {
        FeatureTabItem item = new FeatureTabItem(feature.getFeatureName(), feature.getIconLiteral());
        item.setFeature(feature);
        return item;
    }

    @Override
    public void setStage(Stage stage) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(true);
                stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE));
            } else if (event.getCode() == KeyCode.F9) {
                hideSidebarCheckbox.setSelected(!hideSidebarCheckbox.isSelected());
            }
        });
    }

    @FXML
    void onSystemStateAction() {
        showStage("/ca/warp7/rt/stage/app/SystemState.fxml", "System State", getClass());
    }

    @FXML
    void initialize() {
        dataset.setText("2018iri << 865");
        user.setText("Yu Liu");
        initTabsItemsAndFactory();
        addTabs();
        hideSidebarCheckbox.selectedProperty().addListener((observable, oldValue, selected) -> {
            if (selected) tabsAndContent.getChildren().remove(0);
            else tabsAndContent.getChildren().add(0, appTabs);
        });
        multiTabFeatures.forEach(multiTab -> {
            MenuItem item = new MenuItem();
            item.setText(multiTab.getFeatureName());
            item.setGraphic(FeatureUtils.getIcon(multiTab.getIconLiteral()));
            newButton.getItems().add(item);
        });
    }

    private void initTabsItemsAndFactory() {
        appTabs.setItems(featureTabItems);
        appTabs.setCellFactory(listView -> new ListCell<FeatureTabItem>() {
            @Override
            protected void updateItem(FeatureTabItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (item.isSeparator) {
                    setGraphic(new Separator());
                } else {
                    HBox hBox = AppElement.tabUIFromItem(item);
                    hBox.setOnMouseClicked(event -> tabContent.setCenter(item.getFeature().getViewParent()));
                    setGraphic(hBox);
                }
            }
        });
    }

    private void addTabs() {
        featureTabItems.clear();
        if (baseFeatures.size() > 0) addTabsForFeatures(baseFeatures);
        if (singleTabFeatures.size() > 0) addTabsForFeatures(singleTabFeatures);
        multiTabFeatures.forEach(this::addMultiTab);
        featureTabItems.add(new FeatureTabItem());
    }

    private void addTabsForFeatures(Iterable<Feature> appFeatures) {
        featureTabItems.add(new FeatureTabItem());
        appFeatures.forEach(Feature::onFeatureInit);
        appFeatures.forEach(feature -> featureTabItems.add(fromFeature(feature)));
    }

    private void addMultiTab(Feature.MultiTab multiTab) {
        List<FeatureTabItem> tabs = multiTab.getTabs();
        if (tabs.size() > 0) {
            featureTabItems.add(new FeatureTabItem());
            tabs.forEach(item -> {
                item.setFeature(multiTab);
                featureTabItems.add(item);
            });
        }
    }
}
