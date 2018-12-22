package ca.warp7.rt.feature.scanner;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

class ScannerElement {
    static HBox cellFromEntry(ScannerEntry item) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().bindBidirectional(item.commitProperty);

        Label team = new Label();
        team.textProperty().bind(item.teamProperty);
        team.getStyleClass().add("team-red");
        team.setPrefWidth(50);

        Label scout = new Label();
        scout.textProperty().bind(item.boardScoutProperty);
        scout.setPrefWidth(150);

        Label timestamp = new Label();
        timestamp.textProperty().bind(item.timestampProperty);

        hBox.getChildren().add(checkBox);
        hBox.getChildren().add(timestamp);
        hBox.getChildren().add(team);
        hBox.getChildren().add(scout);

        return hBox;
    }
}
