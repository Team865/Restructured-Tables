package ca.warp7.rt.ext.scanner

import ca.warp7.android.scouting.v5.entry.Alliance
import ca.warp7.android.scouting.v5.entry.V5Entry
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox

fun cellFromEntry(entry: V5Entry): HBox {
    val hBox = HBox()

    hBox.spacing = 10.0
    hBox.alignment = Pos.CENTER_LEFT

    val board = Label()
    board.text = entry.board.name
    board.styleClass.add(when (entry.board.alliance) {
        Alliance.Red -> "team-red"
        Alliance.Blue -> "team-blue"
    })

    val team = Label()
    team.text = entry.team
    team.prefWidth = 50.0

    hBox.children.add(board)
    hBox.children.add(team)

    return hBox
}