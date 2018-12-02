package ca.warp7.tables.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScannerEntry {
    public BooleanProperty commitProperty = new SimpleBooleanProperty();
    public StringProperty teamProperty = new SimpleStringProperty();
    public StringProperty boardScoutProperty = new SimpleStringProperty();
    public StringProperty timestampProperty = new SimpleStringProperty();

    public ScannerEntry(boolean commit, String team, String boardScout, String timestamp) {
        commitProperty.setValue(commit);
        teamProperty.setValue(team);
        boardScoutProperty.setValue(boardScout);
        timestampProperty.set(timestamp);
    }
}
