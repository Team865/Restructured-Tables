package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureItemTab;
import ca.warp7.rt.java.core.ft.FeatureStage;
import javafx.application.Platform;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

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
    @FXML
    FontIcon focusIcon;

    Stage appStage;
    private ObservableList<AppTab> appTabs = FXCollections.observableArrayList();
    private Feature currentFeature = null;
    private FeatureItemTab currentTab = null;
    private BooleanProperty focusedMode = new SimpleBooleanProperty();

    public void initialize() {
        // Defer this initialization so the UI is not blocked
        Platform.runLater(this::initialize0);
    }

    public void toggleFullScreen() {
        appStage.setFullScreen(!appStage.isFullScreen());
    }

    public void toggleFocused() {
        focusedMode.setValue(!focusedMode.get());
    }

    public void reloadTabModel() {
        if (currentFeature == null || currentFeature.onCloseRequest()) {
            tabContent.setCenter(null);
            currentFeature = null;
            currentTab = null;
            appTabs.clear();
            appTabs.add(AppElement.getTeamLogo());
            features.forEach(feature -> feature.getLoadedTabs().forEach(tab -> appTabs.add(new AppTab(tab))));
        }
    }

    public void showMemory() {
        AppElement.showMemoryAlert();
    }

    @Override
    public void setStage(Stage stage) {
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) stage.setFullScreen(true);
            else if (event.getCode() == KeyCode.F9) toggleFocused();
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

    void insertTab(FeatureItemTab newItemTab) {
        ObservableList<AppTab> selectedItems = appTabListView.getSelectionModel().getSelectedItems();
        if (selectedItems.size() == 1) {
            AppTab tab = selectedItems.get(0);
            int index = appTabs.indexOf(tab);
            if (index >= 0) {
                AppTab newTab = new AppTab(newItemTab);
                appTabs.add(index + 1, newTab);
                handleFeatureAction(newItemTab);
                appTabListView.getSelectionModel().select(newTab);
            } else insertLastTab(newItemTab);
        } else insertLastTab(newItemTab);
    }

    boolean removeCurrentTab() {
        ObservableList<AppTab> selectedItems = appTabListView.getSelectionModel().getSelectedItems();
        if (selectedItems.size() == 1 && (currentFeature == null || currentFeature.onCloseRequest())) {
            AppTab tab = selectedItems.get(0);
            appTabs.remove(tab);
            tabContent.setCenter(null);
            currentFeature = null;
            currentTab = null;
            return true;
        }
        return false;
    }

    private void handleFeatureAction(FeatureItemTab tab) {
        if (tab == currentTab) return;
        String id = tab.getFeatureId();
        if (featureMap.containsKey(id)) {
            Feature feature = featureMap.get(id);
            if (currentFeature == feature) {
                currentTab = tab;
                updateTitle(tab);
                currentFeature.onOpenTab(tab);
            } else if (currentFeature == null || currentFeature.onCloseRequest()) {
                updateTitle(tab);
                currentFeature = feature;
                currentTab = tab;
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

    private void insertLastTab(FeatureItemTab tab) {
        appTabs.add(new AppTab(tab));
    }

    private void setupAppTabListView() {
        // Cell factory: either the tab or another referenced UI element
        appTabListView.setCellFactory(listView -> new ListCell<AppTab>() {
            @Override
            protected void updateItem(AppTab item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }
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
        appTabListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        appTabListView.setItems(appTabs);
    }

    private void initialize0() {
        AppUtils.controller = this;
        reloadTabModel();
        setupAppTabListView();
        focusedMode.addListener((observable, oldValue, focused) -> {
            if (focused) {
                tabsAndContent.getChildren().remove(0);
                focusIcon.setIconCode(FontAwesomeSolid.EYE);
            } else {
                tabsAndContent.getChildren().add(0, listViewContainer);
                focusIcon.setIconCode(FontAwesomeSolid.EYE_SLASH);
            }
            if (currentFeature != null) currentFeature.setFocused(focused);
        });
        rowLabel.setText("None");
        columnLabel.setText("None");
        statusMessageLabel.setText("Finished loading app");
    }
}
