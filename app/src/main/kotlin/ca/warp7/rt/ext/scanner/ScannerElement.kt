package ca.warp7.rt.ext.scanner

import ca.warp7.android.scouting.v5.entry.V5Entry
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox

fun cellFromEntry(item: V5Entry): HBox {
    val hBox = HBox()
    hBox.spacing = 10.0
    hBox.alignment = Pos.CENTER_LEFT

    val team = Label()
    team.textProperty().set(item.team)
    team.styleClass.add("team-red")
    team.prefWidth = 50.0

    val scout = Label()
    scout.textProperty().set(item.board.name)
    scout.prefWidth = 150.0

    val timestamp = Label()
    timestamp.textProperty().set(item.timestamp.toString())

    hBox.children.add(timestamp)
    hBox.children.add(team)
    hBox.children.add(scout)

    return hBox
}