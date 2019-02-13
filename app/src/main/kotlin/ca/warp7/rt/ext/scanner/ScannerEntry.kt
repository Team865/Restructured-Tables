package ca.warp7.rt.ext.scanner

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

class ScannerEntry(team: String, boardScout: String, timestamp: String) {
    var teamProperty: StringProperty = SimpleStringProperty()
    var boardScoutProperty: StringProperty = SimpleStringProperty()
    var timestampProperty: StringProperty = SimpleStringProperty()

    init {
        teamProperty.value = team
        boardScoutProperty.value = boardScout
        timestampProperty.set(timestamp)
    }
}
