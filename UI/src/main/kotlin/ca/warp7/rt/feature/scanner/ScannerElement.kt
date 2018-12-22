package ca.warp7.rt.feature.scanner

import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.layout.HBox

internal object ScannerElement {
    @JvmStatic
    fun cellFromEntry(item: ScannerEntry): HBox {
        val hBox = HBox()
        hBox.spacing = 10.0
        hBox.alignment = Pos.CENTER_LEFT

        val checkBox = CheckBox()
        checkBox.selectedProperty().bindBidirectional(item.commitProperty)

        val team = Label()
        team.textProperty().bind(item.teamProperty)
        team.styleClass.add("team-red")
        team.prefWidth = 50.0

        val scout = Label()
        scout.textProperty().bind(item.boardScoutProperty)
        scout.prefWidth = 150.0

        val timestamp = Label()
        timestamp.textProperty().bind(item.timestampProperty)

        hBox.children.add(checkBox)
        hBox.children.add(timestamp)
        hBox.children.add(team)
        hBox.children.add(scout)

        return hBox
    }
}
