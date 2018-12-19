package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureItemTab.Group;
import ca.warp7.rt.java.core.ft.FeatureStage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static ca.warp7.rt.java.app.AppFeatures.featureMap;
import static ca.warp7.rt.java.app.AppFeatures.features;

public class AppController implements FeatureStage {

    private static final Paint gray = Paint.valueOf("gray");
    private static final Paint white = Paint.valueOf("white");

    @FXML
    BorderPane tabContent;
    @FXML
    HBox tabsAndContent;
    @FXML
    ListView<AppActionTab> appActionListView;
    @FXML
    VBox listViewContainer;

    private ObservableList<AppActionTab> appActions = FXCollections.observableArrayList();
    private Feature currentFeature = null;
    private Map<String, ArrayList<FeatureItemTab>> tabGroups = new LinkedHashMap<>();
    private Stage appStage;
    private BooleanProperty hideSidebar = new SimpleBooleanProperty();

    public void toggleFullScreen() {
        appStage.setFullScreen(!appStage.isFullScreen());
    }

    public void toggleSidebar() {
        hideSidebar.setValue(!hideSidebar.get());
    }

    @Override
    public void setStage(Stage stage) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) stage.setFullScreen(true);
            else if (event.getCode() == KeyCode.F9) toggleSidebar();
        });
        stage.setOnCloseRequest(event -> {
            if (currentFeature != null && !currentFeature.onCloseRequest()) {
                event.consume();
            }
        });
        appStage = stage;
        appStage.setMinWidth(800);
        appStage.setMinHeight(450);
        appStage.setMaximized(true);
    }

    @FXML
    void initialize() {
        appActionListView.setItems(appActions);
        appActionListView.setCellFactory(listView -> new ListCell<AppActionTab>() {
            @Override
            protected void updateItem(AppActionTab item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (item.isSeparator()) {
                    setMouseTransparent(true);
                    setFocusTraversable(false);
                    setPrefHeight(15);
                } else {
                    setGraphic(AppElement.tabUIFromAction(item));
                    setOnMouseClicked(event -> handleFeatureAction(item.getFeatureItemTab()));
                }
            }
        });
        features.forEach(feature -> {
            feature.init();
            ObservableList<FeatureItemTab> tabs = feature.getTabObservable();
            tabs.forEach(tab -> {
                String groupName;
                Group group = tab.getTabGroup();
                if (group == Group.SingleTab) groupName = "single";
                else if (group == Group.WithFeature) groupName = tab.getFeatureId();
                else groupName = "unknown";
                if (!tabGroups.containsKey(groupName)) tabGroups.put(groupName, new ArrayList<>());
                tabGroups.get(groupName).add(tab);
            });
        });
        tabGroups.forEach((s, featureActions) -> {
            appActions.add(AppActionTab.separator);
            featureActions.forEach(action -> appActions.add(new AppActionTab(action)));
        });
        hideSidebar.addListener((observable, oldValue, selected) -> {
            if (selected) tabsAndContent.getChildren().remove(0);
            else tabsAndContent.getChildren().add(0, listViewContainer);
        });
        appActionListView.setOnKeyPressed(event -> {
            ObservableList<AppActionTab> selectedItems = appActionListView.getSelectionModel().getSelectedItems();
            if (selectedItems.size() == 1) {
                AppActionTab tab = selectedItems.get(0);
                if (!tab.isSeparator() && event.getCode() == KeyCode.ENTER)
                    handleFeatureAction(tab.getFeatureItemTab());
            }
        });
        appActionListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<AppActionTab>) c -> {
            c.next();
            c.getRemoved().forEach(o -> {
                if (o.getIcon() != null) o.getIcon().setIconColor(gray);
            });
            c.getAddedSubList().forEach(o -> {
                if (o.getIcon() != null) o.getIcon().setIconColor(white);
            });
        });
    }

    @FXML
    void showMemory() {
        AppElement.showMemoryAlert();
    }

    private void handleFeatureAction(FeatureItemTab tab) {
        String id = tab.getFeatureId();
        if (featureMap.containsKey(id)) {
            Feature feature = featureMap.get(id);
            if (currentFeature == feature) currentFeature.onOpenTab(tab);
            else if (currentFeature == null || currentFeature.onCloseRequest()) {
                currentFeature = feature;
                Parent parent = currentFeature.onOpenTab(tab);
                tabContent.setCenter(parent);
            }
        }
    }
}
