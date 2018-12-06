package ca.warp7.rt.java.app;

import ca.warp7.rt.java.core.feature.FeatureAction;
import ca.warp7.rt.java.core.feature.FeatureStage;
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
    private ListView<AppActionTab> appTabs;
    private ObservableList<AppActionTab> featureActions = FXCollections.observableArrayList();

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
        AppFeatures.features.forEach(feature -> {
            feature.init();
            ObservableList<FeatureAction> actionList = feature.getActionList();
            for (FeatureAction action : actionList) {
                switch (action.getType()) {
                    case New:
                        MenuItem item = new MenuItem();
                        item.setText(action.getActionTitle());
                        item.setGraphic(FeatureUtils.getIcon(action.getIconLiteral()));
                        newButton.getItems().add(item);
                        break;
                    case Open:
                        break;
                }
            }
        });
        hideSidebarCheckbox.selectedProperty().addListener((observable, oldValue, selected) -> {
            if (selected) tabsAndContent.getChildren().remove(0);
            else tabsAndContent.getChildren().add(0, appTabs);
        });
    }

    private void initTabsItemsAndFactory() {
        appTabs.setItems(featureActions);
        appTabs.setCellFactory(listView -> new ListCell<AppActionTab>() {
            @Override
            protected void updateItem(AppActionTab item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) return;
                if (item.isSeparator()) {
                    setGraphic(new Separator());
                } else {
                    HBox hBox = AppElement.tabUIFromAction(item.getFeatureAction());
                    hBox.setOnMouseClicked(event -> handleFeatureAction(item.getFeatureAction()));
                    setGraphic(hBox);
                }
            }
        });
    }

    private void handleFeatureAction(FeatureAction action) {
    }
}
