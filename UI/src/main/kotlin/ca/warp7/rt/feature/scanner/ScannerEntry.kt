package ca.warp7.rt.feature.scanner

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class ScannerEntry(commit: Boolean, team: String, boardScout: String, timestamp: String) {
    var commitProperty: BooleanProperty = SimpleBooleanProperty()
    var teamProperty: StringProperty = SimpleStringProperty()
    var boardScoutProperty: StringProperty = SimpleStringProperty()
    var timestampProperty: StringProperty = SimpleStringProperty()

    init {
        commitProperty.value = commit
        teamProperty.value = team
        boardScoutProperty.value = boardScout
        timestampProperty.set(timestamp)
    }
}
