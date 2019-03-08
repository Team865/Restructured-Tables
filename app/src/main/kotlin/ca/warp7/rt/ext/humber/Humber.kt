@file:Suppress("unused")

package ca.warp7.rt.ext.humber

import ca.warp7.android.scouting.v5.boardfile.exampleBoardfile
import ca.warp7.android.scouting.v5.entry.V5Entry
import ca.warp7.rt.ext.scanner.DecodedEntry
import krangl.dataFrameOf
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
object Humber {
    val root: File = File(System.getProperty("user.home") + "/Desktop/HumberRawData").apply { mkdirs() }

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

object GamePieces {
    const val Cargo = 0
    const val None = 1
    const val Hatch = 2
}

val startPositions = arrayOf("None", "L2", "L1", "C1", "R1", "R2")
val climbLevels = arrayOf("None", "1", "2", "3")
val liftingLevels = arrayOf("None", "2", "3")
val gamePieces = arrayOf("Cargo", "None", "Hatch")

val longFormatter = SimpleDateFormat("yyyy-dd-MM HH:mm:ss")

fun Boolean.toInt() = if (this) 1 else 0

fun process(data: List<V5Entry>) = dataFrameOf(data.map { it.toRow() })

@Suppress("unused", "CanBeVal")
fun V5Entry.toRow(): Map<String, Any> {
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
    var hatchAcquired = 0
    var cargoAcquired = 0
    var defendingCount = 0
    var totalDefendingTime = 0
    var defendedCount = 0
    var totalDefendedTime = 0
    var outtakeWhileDefending = 0
    var outtakeNoGamePiece = 0
    var illegalGamePiece = 0

    var currentGamePiece = GamePieces.None
    var gamePieceInSandstorm = false
    var isLeftFieldArea = true
    dataPoints.forEach {
        when (it.type) {
            Sandstorm.gamePiece -> {
                if (!gamePieceInSandstorm) {
                    gamePieceInSandstorm = true
                    currentGamePiece = GamePieces.None
                }
                when (it.value) {
                    GamePieces.Hatch -> {
                        hatchAcquired++
                        currentGamePiece = GamePieces.Hatch
                        if (currentGamePiece != GamePieces.None) {
                            illegalGamePiece++
                        }
                    }
                    GamePieces.Cargo -> {
                        cargoAcquired++
                        currentGamePiece = GamePieces.Cargo
                        if (currentGamePiece != GamePieces.None) {
                            illegalGamePiece++
                        }
                    }
                }
            }
            Teleop.gamePiece -> {
                if (gamePieceInSandstorm) {
                    gamePieceInSandstorm = false
                    currentGamePiece = GamePieces.None
                }
                when (it.value) {
                    GamePieces.Hatch -> {
                        hatchAcquired++
                        currentGamePiece = GamePieces.Hatch
                        if (currentGamePiece != GamePieces.None) {
                            illegalGamePiece++
                        }
                    }
                    GamePieces.Cargo -> {
                        cargoAcquired++
                        currentGamePiece = GamePieces.Cargo
                        if (currentGamePiece != GamePieces.None) {
                            illegalGamePiece++
                        }
                    }
                }
            }
            Sandstorm.fieldArea -> {
                isLeftFieldArea = !isLeftFieldArea
            }
            Sandstorm.rocket -> {
                when (currentGamePiece) {
                    GamePieces.Cargo -> {
                        if (isLeftFieldArea) ssLeftRocketCargo++
                        else ssRightRocketCargo++
                    }
                    GamePieces.Hatch -> {
                        if (isLeftFieldArea) ssLeftRocketHatch++
                        else ssRightRocketHatch++
                    }
                    GamePieces.None -> outtakeNoGamePiece++
                }
            }
            Sandstorm.frontCargoShip -> {
                when (currentGamePiece) {
                    GamePieces.Cargo -> ssFrontCargo++
                    GamePieces.Hatch -> ssFrontHatch++
                    GamePieces.None -> outtakeNoGamePiece++
                }
            }
            Sandstorm.sideCargoShip -> {
                when (currentGamePiece) {
                    GamePieces.Cargo -> {
                        if (isLeftFieldArea) ssLeftSideCargo++
                        else ssRightSideCargo++
                    }
                    GamePieces.Hatch -> {
                        if (isLeftFieldArea) ssLeftSideHatch++
                        else ssRightSideHatch++
                    }
                    GamePieces.None -> outtakeNoGamePiece++
                }
            }
        }
    }
    return mapOf(
            "Match" to match,
            "Team" to team,
            "Alliance" to board.alliance.toString(),
            "Scout" to scout,
            "Board" to board.toString(),
            "Starting Position" to startPositions[lastValue(Sandstorm.startPosition)?.value ?: 0],
            "Starting Game Piece" to gamePieces[dataPoints
                    .lastOrNull { it.type == Sandstorm.gamePiece && it.time == 0 }?.value ?: 1],
            "Hab Line" to (lastValue(Sandstorm.habLine)?.value ?: 0),
            "Total Hatch Acquired" to hatchAcquired,
            "Total Hatch Placed" to
                    ssLeftRocketHatch
                    + ssRightRocketHatch
                    + ssLeftSideHatch
                    + ssRightSideHatch
                    + ssFrontHatch
                    + cargoShipHatch
                    + rocket1Hatch
                    + rocket2Hatch
                    + rocket3Hatch,
            "Total Cargo Acquired" to cargoAcquired,
            "Total Cargo Placed" to
                    ssLeftRocketCargo
                    + ssRightRocketCargo
                    + ssLeftSideCargo
                    + ssRightSideCargo
                    + ssFrontCargo
                    + cargoShipCargo
                    + rocket1Cargo
                    + rocket2Cargo
                    + rocket3Cargo,
            "Camera Controlled" to dataPoints.count { it.type == Sandstorm.cameraControl && it.value == 1 },
            "SS Crossed Mid-line" to dataPoints.any { it.time == Sandstorm.fieldArea && it.time != 0 }.toInt(),
            "SS Left Rocket Hatch" to ssLeftRocketHatch,
            "SS Left Rocket Cargo" to ssLeftRocketCargo,
            "SS Right Rocket Hatch" to ssRightRocketHatch,
            "SS Right Rocket Cargo" to ssRightRocketCargo,
            "SS Left Side Hatch" to ssLeftSideHatch,
            "SS Left Side Cargo" to ssLeftSideCargo,
            "SS Right Side Hatch" to ssRightSideHatch,
            "SS Right Side Cargo" to ssRightSideCargo,
            "SS Front Hatch" to ssFrontHatch,
            "SS Front Cargo" to ssFrontCargo,
            "Cargo Ship Hatch" to cargoShipHatch,
            "Cargo Ship Cargo" to cargoShipCargo,
            "Rocket 1 Hatch" to rocket1Hatch,
            "Rocket 1 Cargo" to rocket1Cargo,
            "Rocket 2 Hatch" to rocket2Hatch,
            "Rocket 2 Cargo" to rocket2Cargo,
            "Rocket 3 Hatch" to rocket3Hatch,
            "Rocket 3 Cargo" to rocket3Cargo,
            "Defending Count" to defendingCount,
            "Total Defending Time" to totalDefendingTime,
            "Defended Count" to defendedCount,
            "Total Defended Time" to totalDefendedTime,
            "Climb Level" to climbLevels[lastValue(Endgame.climbLevel)?.value ?: 0],
            "Assisted Climb" to (lastValue(Endgame.assistedClimb)?.value ?: 0),
            "Lifting 1" to liftingLevels[lastValue(Endgame.liftingRobot1)?.value ?: 0],
            "Lifting 2" to liftingLevels[lastValue(Endgame.liftingRobot2)?.value ?: 0],
            "Start Time" to longFormatter.format(Date(timestamp * 1000L)),
            "Undo" to undone,
            "Outtake While Defending" to outtakeWhileDefending,
            "Outtake No Game Piece" to outtakeNoGamePiece,
            "Illegal Game Piece" to illegalGamePiece,
            "Comments" to comments
    )
}