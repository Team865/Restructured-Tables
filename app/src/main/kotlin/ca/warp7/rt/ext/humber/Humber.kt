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

fun Boolean.toInt() = if (this) 1 else 0

@Suppress("unused", "CanBeVal")
fun process(data: List<V5Entry>): DataFrame {
    val rows = mutableListOf<Map<String, Any>>()
    for (entry in data) {
        var gamePiece = 0
        var opponentField = 0
        var defended = 0
        var ssLeftRocketHatch = 0
        var ssLeftRocketCargo = 0
        var ssRightRocketHatch = 0
        var ssRightRocketCargo = 0
        var ssLeftSideHatch = 0
        var ssLeftSideCargo = 0
        var ssRightSideHatch = 0
        var ssRightSideCargo = 0
        var ssFrontHatch = 0
        var ssFrontCargo = 0
        var cargoShipCargo = 0
        var cargoShipHatch = 0
        var rocket1Hatch = 0
        var rocket1Cargo = 0
        var rocket2Hatch = 0
        var rocket2Cargo = 0
        var rocket3Hatch = 0
        var rocket3Cargo = 0
        var droppedHatch = 0
        var droppedCargo = 0
        var defendingCount = 0
        var totalDefendingTime = 0
        var defendedCount = 0
        var totalDefendedTime = 0

        rows.add(mapOf(
                "Match" to entry.match,
                "Team" to entry.team,
                "Alliance" to entry.board.alliance.toString(),
                "Scout" to entry.scout,
                "Board" to entry.board.toString(),
                "Starting Position" to startPositions[entry.lastValue(Sandstorm.startPosition)?.value ?: 0],
                "Starting Game Piece" to gamePieces[entry.dataPoints
                        .lastOrNull { it.type == Sandstorm.gamePiece && it.time == 0 }?.value ?: 0],
                "Hab Line" to (entry.lastValue(Sandstorm.habLine)?.value ?: 0),
                "Total Hatch Placed" to 0,
                "Total Cargo Placed" to 0,
                "Camera Controlled" to entry.dataPoints.count { it.type == Sandstorm.cameraControl && it.value == 1 },
                "SS Crossed Mid-line" to entry.dataPoints.any { it.time == Sandstorm.fieldArea && it.time != 0 }.toInt(),
                "SS Left Rocket Hatch" to ssLeftRocketHatch,
                "SS Left Rocket Cargo" to ssLeftRocketCargo,
                "SS Right Rocket Hatch" to ssRightRocketHatch,
                "SS Right Rocket Cargo" to ssRightRocketCargo,
                "SS Left Side Cargo" to ssLeftSideCargo,
                "SS Left Side Hatch" to ssLeftSideHatch,
                "SS Right Side Cargo" to ssRightSideCargo,
                "SS Right Side Hatch" to ssRightSideHatch,
                "SS Front Cargo" to ssFrontCargo,
                "SS Front Hatch" to ssFrontHatch,
                "Cargo Ship Cargo" to cargoShipCargo,
                "Cargo Ship Hatch" to cargoShipHatch,
                "Rocket 1 Hatch" to rocket1Hatch,
                "Rocket 1 Cargo" to rocket1Cargo,
                "Rocket 2 Hatch" to rocket2Hatch,
                "Rocket 2 Cargo" to rocket2Cargo,
                "Rocket 3 Hatch" to rocket3Hatch,
                "Rocket 3 Cargo" to rocket3Cargo,
                "Dropped Hatch" to droppedHatch,
                "Dropped Cargo" to droppedCargo,
                "Defending Count" to defendingCount,
                "Total Defending Time" to totalDefendingTime,
                "Defended Count" to defendedCount,
                "Total Defended Time" to totalDefendedTime,
                "Climb Level" to climbLevels[entry.lastValue(Endgame.climbLevel)?.value ?: 0],
                "Assisted Climb" to (entry.lastValue(Endgame.assistedClimb)?.value ?: 0),
                "Lifting 1" to liftingLevels[entry.lastValue(Endgame.liftingRobot1)?.value ?: 0],
                "Lifting 2" to liftingLevels[entry.lastValue(Endgame.liftingRobot2)?.value ?: 0],
                "Start Time" to longFormatter.format(Date(entry.timestamp * 1000L)),
                "Undo" to entry.undone,
                "Outtake While Defending" to 0,
                "Outtake No Game Piece" to 0,
                "Comments" to entry.comments
        ))
    }
    return dataFrameOf(rows)
}
