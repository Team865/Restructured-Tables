package ca.warp7.rt.ext.scanner

import ca.warp7.android.scouting.v5.entry.Alliance
import ca.warp7.android.scouting.v5.entry.V5Entry
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import org.kordamp.ikonli.javafx.FontIcon
import java.text.SimpleDateFormat
import java.util.*

private val formatter = SimpleDateFormat("HH:mm:ss")

fun cellFromEntry(entry: V5Entry): Parent {
    val hBox = HBox()

    hBox.spacing = 5.0
    hBox.alignment = Pos.CENTER_LEFT

    val match = Label()
    match.minWidth = 72.0
    match.text = entry.match.split("_").last()
    match.graphic = FontIcon().apply { iconLiteral = "fas-rocket:16:404040" }

    val board = Label()
    board.text = entry.board.name
    board.minWidth = 36.0
    board.styleClass.add(when (entry.board.alliance) {
        Alliance.Red -> "team-red"
        Alliance.Blue -> "team-blue"
    })

    board.graphic = FontIcon().apply { iconLiteral = "far-clipboard:16:404040" }

    val team = Label()
    team.text = entry.team
    team.style = "-fx-font-weight: bold"
    team.minWidth = 54.0

    team.graphic = FontIcon().apply { iconLiteral = "far-user:16:404040" }

    val startTime = Label()
    startTime.text = formatter.format(Date(entry.timestamp * 1000L))
    startTime.graphic = FontIcon().apply { iconLiteral = "far-clock:16:404040" }

    hBox.children.addAll(match, board, team, startTime)

    val vBox = VBox()
    vBox.spacing = 5.0

    vBox.padding = Insets(5.0, 0.0, 5.0, 0.0)

    val scoutAndComments = Label()
    scoutAndComments.text = entry.scout + " - " + if (entry.comments.isNotEmpty()) entry.comments else "No Comments"
    scoutAndComments.style = "-fx-text-fill: #404040"

    vBox.children.addAll(hBox, scoutAndComments)

    return vBox
}