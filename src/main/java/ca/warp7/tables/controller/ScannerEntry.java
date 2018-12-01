package ca.warp7.tables.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScannerEntry {
    BooleanProperty commitProperty = new SimpleBooleanProperty();
    StringProperty teamProperty = new SimpleStringProperty();
    StringProperty boardProperty = new SimpleStringProperty();
    StringProperty scoutProperty = new SimpleStringProperty();

    public ScannerEntry(boolean commit, String team, String board, String scout) {
        commitProperty.setValue(commit);
        teamProperty.setValue(team);
        boardProperty.setValue(board);
        scoutProperty.setValue(scout);
    }

    public boolean isCommitProperty() {
        return commitProperty.get();
    }

    public BooleanProperty commitPropertyProperty() {
        return commitProperty;
    }

    public void setCommitProperty(boolean commitProperty) {
        this.commitProperty.set(commitProperty);
    }

    public String getTeamProperty() {
        return teamProperty.get();
    }

    public StringProperty teamPropertyProperty() {
        return teamProperty;
    }

    public void setTeamProperty(String teamProperty) {
        this.teamProperty.set(teamProperty);
    }

    public String getBoardProperty() {
        return boardProperty.get();
    }

    public StringProperty boardPropertyProperty() {
        return boardProperty;
    }

    public void setBoardProperty(String boardProperty) {
        this.boardProperty.set(boardProperty);
    }

    public String getScoutProperty() {
        return scoutProperty.get();
    }

    public StringProperty scoutPropertyProperty() {
        return scoutProperty;
    }

    public void setScoutProperty(String scoutProperty) {
        this.scoutProperty.set(scoutProperty);
    }
}
