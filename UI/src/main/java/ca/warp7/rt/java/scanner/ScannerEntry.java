package ca.warp7.rt.java.scanner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class ScannerEntry {
    BooleanProperty commitProperty = new SimpleBooleanProperty();
    StringProperty teamProperty = new SimpleStringProperty();
    StringProperty boardScoutProperty = new SimpleStringProperty();
    StringProperty timestampProperty = new SimpleStringProperty();

    ScannerEntry(boolean commit, String team, String boardScout, String timestamp) {
        commitProperty.setValue(commit);
        teamProperty.setValue(team);
        boardScoutProperty.setValue(boardScout);
        timestampProperty.set(timestamp);
    }
}
