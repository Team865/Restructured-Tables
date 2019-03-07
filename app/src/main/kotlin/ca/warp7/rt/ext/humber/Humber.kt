@file:Suppress("unused")

package ca.warp7.rt.ext.humber

import ca.warp7.android.scouting.v5.boardfile.exampleBoardfile
import ca.warp7.android.scouting.v5.entry.V5Entry
import ca.warp7.rt.ext.scanner.DecodedEntry
import krangl.DataFrame
import krangl.dataFrameOf
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
object Humber {
    val root: File = File(System.getProperty("user.home") + "/Desktop/HumberScouting").apply { mkdirs() }

    fun getData(): List<V5Entry> = root.listFiles().map { it.readLines() }.flatten().toSet().map { DecodedEntry(it) }
}

val template = exampleBoardfile.robotScoutTemplate

object Sandstorm {
    val startPosition = template.lookup("start_position")
    val habLine = template.lookup("hab_line")
    val cameraControl = template.lookup("camera_control")
    val fieldArea = template.lookup("sandstorm_field_area")
    val rocket = template.lookup("rocket")
    val frontCargoShip = template.lookup("front_cargo_ship")
    val sideCargoShip = template.lookup("side_cargo_ship")
    val gamePiece = template.lookup("sandstorm_game_piece")
}

object Teleop {
    val rocket3 = template.lookup("rocket_3")
    val opponentField = template.lookup("opponent_field")
    val rocket2 = template.lookup("rocket_2")
    val defended = template.lookup("defended")
    val rocket1 = template.lookup("rocket_1")
    val cargoShip = template.lookup("cargo_ship")
    val gamePiece = template.lookup("game_piece")
}

object Endgame {
    val climbLevel = template.lookup("climb_level")
    val assistedClimb = template.lookup("assisted_climb")
    val liftingRobot1 = template.lookup("lifting_robot_1")
    val liftingRobot2 = template.lookup("lifting_robot_2")
}

val startPositions = listOf("None", "L2", "L1", "C1", "R1", "R2")
val climbLevels = listOf("None", "1", "2", "3")
val liftingLevels = listOf("None", "2", "3")
val gamePieces = listOf("Cargo", "None", "Hatch")

val longFormatter = SimpleDateFormat("yyyy-dd-MM HH:mm:ss")

@Suppress("unused")
fun process(data: List<V5Entry>): DataFrame {
    val rows = mutableListOf<Map<String, Any>>()
    for (entry in data) {
        var gamePiece = 0
        var opponentField = 0
        var defended = 0

        rows.add(mapOf(
                "Match" to entry.match,
                "Team" to entry.team,
                "Alliance" to entry.board.alliance.toString(),
                "Scout" to entry.scout,
                "Board" to entry.board.toString(),
                "Starting Position" to startPositions[entry.lastValue(Sandstorm.startPosition)?.value ?: 0],
                "Starting Game Piece" to gamePieces[entry.dataPoints
                        .firstOrNull { it.type == Sandstorm.gamePiece && it.time == 0 }?.value ?: 0],
                "Hab Line" to (entry.lastValue(Sandstorm.habLine)?.value ?: 0),
                "Camera Controlled" to entry.dataPoints.count { it.type == Sandstorm.cameraControl && it.value == 1 },
                "Total Cargo" to 0,
                "Total Hatch" to 0,
                "SS Crossed Mid-line" to 0,
                "SS Left Rocket Hatch" to 0,
                "SS Left Rocket Cargo" to 0,
                "SS Right Rocket Hatch" to 0,
                "SS Right Rocket Cargo" to 0,
                "SS Left Side Cargo" to 0,
                "SS Left Side Hatch" to 0,
                "SS Right Side Cargo" to 0,
                "SS Right Side Hatch" to 0,
                "SS Front Cargo" to 0,
                "SS Front Hatch" to 0,
                "Cargo Ship Cargo" to 0,
                "Cargo Ship Hatch" to 0,
                "Rocket 1 Cargo" to 0,
                "Rocket 2 Cargo" to 0,
                "Rocket 3 Cargo" to 0,
                "Rocket 1 Hatch" to 0,
                "Rocket 2 Hatch" to 0,
                "Rocket 3 Hatch" to 0,
                "Crossed Center Line" to 0,
                "Total Defending Time" to 0,
                "Defended Count" to 0,
                "Total Defended Time" to 0,
                "Climb Level" to climbLevels[entry.lastValue(Endgame.climbLevel)?.value ?: 0],
                "Assisted Climb" to (entry.lastValue(Endgame.assistedClimb)?.value ?: 0),
                "Lifting 1" to liftingLevels[entry.lastValue(Endgame.liftingRobot1)?.value ?: 0],
                "Lifting 2" to liftingLevels[entry.lastValue(Endgame.liftingRobot2)?.value ?: 0],
                "Start Time" to longFormatter.format(Date(entry.timestamp * 1000L)),
                "Undo" to entry.undone,
                "Outtake No Game Piece" to 0,
                "Comments" to entry.comments
        ))
    }
    return dataFrameOf(rows)
}
