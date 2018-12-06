package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.ft.Feature;
import ca.warp7.rt.java.core.ft.FeatureAction;
import ca.warp7.rt.java.core.ft.FeatureStage;
import ca.warp7.rt.java.core.ft.FeatureUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static ca.warp7.rt.java.app.AppFeatures.featureMap;
import static ca.warp7.rt.java.app.AppFeatures.features;
import static ca.warp7.rt.java.core.ft.FeatureUtils.showStage;

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
    private ListView<AppActionTab> appActionListView;
    private ObservableList<AppActionTab> appActions = FXCollections.observableArrayList();
    private Feature currentFeature;

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
        initTabFactory();
        Map<String, ArrayList<FeatureAction>> tabGroups = new LinkedHashMap<>();
        features.forEach(feature -> {
            feature.init();
            feature.getActionList().forEach(action -> {
                switch (action.getType()) {
                    case New:
                        MenuItem item = new MenuItem();
                        item.setText(action.getActionTitle());
                        item.setGraphic(FeatureUtils.getIcon(action.getIconLiteral()));
                        item.setOnAction(event -> handleFeatureAction(action));
                        newButton.getItems().add(item);
                        break;
                    case TabItem:
                        String groupName;
                        switch (action.getActionGroup()) {
                            case Core:
                                groupName = "core";
                                break;
                            case SingleTab:
                                groupName = "single";
                                break;
                            case WithFeature:
                                groupName = action.getFeatureId();
                                break;
                            default:
                                groupName = "unknown";
                        }
                        if (!tabGroups.containsKey(groupName)) tabGroups.put(groupName, new ArrayList<>());
                        tabGroups.get(groupName).add(action);
                }
            });
        });
        tabGroups.forEach((s, featureActions) -> {
            appActions.add(AppActionTab.separator);
            featureActions.forEach(action -> appActions.add(new AppActionTab(action)));
        });
        appActions.add(AppActionTab.separator);
        hideSidebarCheckbox.selectedProperty().addListener((observable, oldValue, selected) -> {
            if (selected) tabsAndContent.getChildren().remove(0);
            else tabsAndContent.getChildren().add(0, appActionListView);
        });
    }

    private void initTabFactory() {
        appActionListView.setItems(appActions);
        appActionListView.setCellFactory(listView -> new ListCell<AppActionTab>() {
            @Override
            protected void updateItem(AppActionTab item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (!item.isSeparator()) {
                    HBox hBox = AppElement.tabUIFromAction(item.getFeatureAction());
                    hBox.setOnMouseClicked(event -> handleFeatureAction(item.getFeatureAction()));
                    setGraphic(hBox);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    private void handleFeatureAction(FeatureAction action) {
        String id = action.getFeatureId();
        if (featureMap.containsKey(id)) {
            Feature feature = featureMap.get(id);
            if (currentFeature == feature) {
                currentFeature.onAction(action.getType(), action.getParamString());
            } else if (currentFeature == null || currentFeature.onCloseRequest()) {
                currentFeature = feature;
                Parent parent = currentFeature.onAction(action.getType(), action.getParamString());
                tabContent.setCenter(parent);
            }
        }
    }
}
