package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureStage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
    ListView<AppTab> appTabListView;
    @FXML
    VBox listViewContainer;
    @FXML
    Label statusMessageLabel;
    @FXML
    Label rowLabel;
    @FXML
    Label columnLabel;

    Stage appStage;
    private ObservableList<AppTab> appTabs = FXCollections.observableArrayList();
    private Feature currentFeature = null;
    private BooleanProperty hideSidebar = new SimpleBooleanProperty();

    @FXML
    void initialize() {
        AppUtils.instance = this;
        appTabs.add(AppElement.getTeamLogo());
        features.forEach(feature -> feature.getLoadedTabs().forEach(tab -> appTabs.add(new AppTab(tab))));
        setupAppTabListView();
        hideSidebar.addListener((observable, oldValue, selected) -> {
            if (selected) tabsAndContent.getChildren().remove(0);
            else tabsAndContent.getChildren().add(0, listViewContainer);
        });
        rowLabel.setText("None");
        columnLabel.setText("None");
    }

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
    void showMemory() {
        AppElement.showMemoryAlert();
    }

    private void handleFeatureAction(FeatureItemTab tab) {
        String id = tab.getFeatureId();
        if (featureMap.containsKey(id)) {
            Feature feature = featureMap.get(id);
            if (currentFeature == feature) {
                updateTitle(tab);
                currentFeature.onOpenTab(tab);
            } else if (currentFeature == null || currentFeature.onCloseRequest()) {
                updateTitle(tab);
                currentFeature = feature;
                Parent parent = currentFeature.onOpenTab(tab);
                tabContent.setCenter(parent);
                rowLabel.setText("None");
                columnLabel.setText("None");
            }
        }
    }

    private void updateTitle(FeatureItemTab tab) {
        String title = AppElement.getTitle(tab);
        AppUtils.setStatusMessage("Opened tab '" + title + "'");
        appStage.setTitle(title);
    }

    private void setupAppTabListView() {
        // Cell factory: either the tab or another referenced UI element
        appTabListView.setCellFactory(listView -> new ListCell<AppTab>() {
            @Override
            protected void updateItem(AppTab item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (item.isDecorativeNode()) {
                    setMouseTransparent(true);
                    setFocusTraversable(false);
                    setGraphic(item.getDecorativeNode());
                    setPrefHeight(item.getDecorativeHeight());
                } else {
                    setGraphic(AppElement.tabUIFromAction(item));
                    setOnMouseClicked(event -> handleFeatureAction(item.getFeatureItemTab()));
                }
            }
        });
        // Key press: switch to tab on enter
        appTabListView.setOnKeyPressed(event -> {
            ObservableList<AppTab> selectedItems = appTabListView.getSelectionModel().getSelectedItems();
            if (selectedItems.size() == 1) {
                AppTab tab = selectedItems.get(0);
                if (!tab.isDecorativeNode() && event.getCode() == KeyCode.ENTER)
                    handleFeatureAction(tab.getFeatureItemTab());
            }
        });
        // Selection change: change icon colour
        appTabListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<AppTab>) c -> {
            while (c.next()) {
                c.getRemoved().forEach(o -> {
                    if (o.getIcon() != null) o.getIcon().setIconColor(gray);
                });
                c.getAddedSubList().forEach(o -> {
                    if (o.getIcon() != null) o.getIcon().setIconColor(white);
                });
            }
        });
        // Focus change: change icon color
        appTabListView.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                appTabListView.getSelectionModel().getSelectedItems().forEach(tab -> {
                    if (tab.getIcon() != null) tab.getIcon().setIconColor(gray);
                });
            }
        });
        appTabListView.setItems(appTabs);
    }
}
