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

    fun process(data: List<V5Entry>) = dataFrameOf(data.map { it.toRow() })
}

val template = exampleBoardfile.robotScoutTemplate

object Sandstorm {
    val startPosition = template.lookup("start_position")
    val habLine = template.lookup("hab_line")
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


private fun <K> MutableMap<K, Int>.inc(k: K) = plus(k, 1)

private fun <K> MutableMap<K, Int>.plus(k: K, n: Int) {
    this[k] = n + this[k]!!
}

fun V5Entry.toRow(): Map<String, Any> {
    val defining = mapOf(
            "Match" to match,
            "Team" to team,
            "Alliance" to board.alliance.toString(),
            "Scout" to scout,
            "Board" to board.toString(),
            "Start Time" to longFormatter.format(Date(timestamp * 1000L))
    )
    val gamepieces = mutableMapOf<String, Int>()
    val defence = mutableMapOf(
            "Defending count" to 0, "Defending time" to 0,
            "Defended count" to 0, "Defending time" to 0
    )
    val errors = mutableMapOf(
            "ERROR outtake no gamepiece" to 0,
            "ERROR outtake while on opponent field" to 0
    )
    val climb = mutableMapOf(
            "Climb Level" to ca.warp7.rt.ext.humber.climbLevels[lastValue(Endgame.climbLevel)?.value ?: 0],
            "Climb 2" to (ca.warp7.rt.ext.humber.climbLevels[lastValue(Endgame.climbLevel)?.value ?: 0] == "2").toInt(),
            "Climb 3" to (ca.warp7.rt.ext.humber.climbLevels[lastValue(Endgame.climbLevel)?.value ?: 0] == "3").toInt(),
            "Assisted Climb" to (lastValue(Endgame.assistedClimb)?.value ?: 0),
            "Lifting 1" to ca.warp7.rt.ext.humber.liftingLevels[lastValue(Endgame.liftingRobot1)?.value ?: 0],
            "Lifting 2" to ca.warp7.rt.ext.humber.liftingLevels[lastValue(Endgame.liftingRobot2)?.value ?: 0]
    )
    val autonActions  = emptyList<String>().toMutableList()
    val misc = mutableMapOf(
            "Comments" to comments,
            "Start position" to "None",
            "Starting Position" to startPositions[lastValue(Sandstorm.startPosition)?.value ?: 0],
            "Starting Game Piece" to gamePieces[dataPoints.lastOrNull {
                it.type == Sandstorm.gamePiece && it.time == 0
            }?.value ?: 1],
            "Hab Line" to (lastValue(Sandstorm.habLine)?.value ?: 0)
    )

    listOf("Hatch", "Cargo").forEach { gamepiece ->
        // SS
        gamepieces["SS, $gamepiece, Acquired"] = 0
        gamepieces["SS, $gamepiece, Front cargo ship"] = 0
        listOf("Left", "Right").forEach { side ->
            gamepieces["SS, $gamepiece, $side cargo ship"] = 0
            gamepieces["SS, $gamepiece, $side rocket"] = 0
        }
        // Tele
        gamepieces["Tele, $gamepiece, Acquired"] = 0
        gamepieces["Tele, $gamepiece, Cargo ship"] = 0
        (1 until 3).forEach { level ->
            gamepieces["Tele, $gamepiece, Rocket $level"] = 0
        }
    }

    var currentGamePiece = GamePieces.None
    var gamePieceInSandstorm = false
    var isLeftFieldArea = true
    var isOnOpponentField = false
    var lastOpponentFieldTime = 0
    var isDefended = false
    var lastDefendedTime = 0
    dataPoints.forEach {
        when (it.type) {
            Sandstorm.gamePiece -> {
                if (!gamePieceInSandstorm) {
                    gamePieceInSandstorm = true
                    currentGamePiece = GamePieces.None
                }
                when (it.value) {
                    GamePieces.Hatch -> {
                        autonActions += "Acquired, Hatch"
                        gamepieces.inc("SS, Hatch, Acquired")
                        currentGamePiece = GamePieces.Hatch
                    }
                    GamePieces.Cargo -> {
                        autonActions += "Acquired, Cargo"
                        gamepieces.inc("SS, Cargo, Acquired")
                        currentGamePiece = GamePieces.Cargo
                    }
                }
            }
            Sandstorm.fieldArea -> {
                isLeftFieldArea = !isLeftFieldArea
            }
            Sandstorm.rocket -> {
                when (currentGamePiece) {
                    GamePieces.Cargo -> {
                        if (isLeftFieldArea) {
                            autonActions += "Left rocket, Cargo"
                            gamepieces.inc("SS, Cargo, Left rocket")
                        }
                        else {
                            autonActions += "Right rocket, Cargo"
                            gamepieces.inc("SS, Cargo, Right rocket")
                        }
                    }
                    GamePieces.Hatch -> {
                        if (isLeftFieldArea) {
                            autonActions += "Left rocket, Hatch"
                            gamepieces.inc("SS, Hatch, Left rocket")
                        }
                        else {
                            autonActions += "Right rocket, Hatch"
                            gamepieces.inc("SS, Hatch, Right rocket")
                        }
                    }
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Sandstorm.frontCargoShip -> {
                when (currentGamePiece) {
                    GamePieces.Cargo -> {
                        autonActions += "Front cargo ship, Cargo"
                        gamepieces.inc("SS, Cargo, Front cargo ship")
                    }
                    GamePieces.Hatch -> {
                        autonActions += "Front cargo ship, Hatch"
                        gamepieces.inc("SS, Hatch, Front cargo ship")
                    }
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Sandstorm.sideCargoShip -> {
                when (currentGamePiece) {
                    GamePieces.Cargo -> {
                        if (isLeftFieldArea) {
                            autonActions += "Left cargo ship, Cargo"
                            gamepieces.inc("SS, Cargo, Left cargo ship")
                        }
                        else {
                            autonActions += "Right cargo ship, Cargo"
                            gamepieces.inc("SS, Cargo, Right cargo ship")
                        }
                    }
                    GamePieces.Hatch -> {
                        if (isLeftFieldArea){
                            autonActions += "Left cargo ship, Hatch"
                            gamepieces.inc("SS, Hatch, Left cargo ship")
                        }
                        else {
                            autonActions += "Right cargo ship, Hatch"
                            gamepieces.inc("SS, Hatch, Right cargo ship")
                        }
                    }
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Teleop.gamePiece -> {
                if (gamePieceInSandstorm) {
                    gamePieceInSandstorm = false
                    currentGamePiece = GamePieces.None
                }
                when (it.value) {
                    GamePieces.Hatch -> {
                        gamepieces.inc("Tele, Hatch, Acquired")
                        currentGamePiece = GamePieces.Hatch
                    }
                    GamePieces.Cargo -> {
                        gamepieces.inc("Tele, Cargo, Acquired")
                        currentGamePiece = GamePieces.Cargo
                    }
                }
            }
            Teleop.rocket1 -> {
                if (isOnOpponentField) errors.inc("ERROR outtake while on opponent field")
                when (currentGamePiece) {
                    GamePieces.Cargo -> gamepieces.inc("Tele, Cargo, Rocket 1")
                    GamePieces.Hatch -> gamepieces.inc("Tele, Hatch, Rocket 1")
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Teleop.rocket2 -> {
                if (isOnOpponentField) errors.inc("ERROR outtake while on opponent field")
                when (currentGamePiece) {
                    GamePieces.Cargo -> gamepieces.inc("Tele, Cargo, Rocket 2")
                    GamePieces.Hatch -> gamepieces.inc("Tele, Hatch, Rocket 2")
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Teleop.rocket3 -> {
                if (isOnOpponentField) errors.inc("ERROR outtake while on opponent field")
                when (currentGamePiece) {
                    GamePieces.Cargo -> gamepieces.inc("Tele, Cargo, Rocket 3")
                    GamePieces.Hatch -> gamepieces.inc("Tele, Hatch, Rocket 3")
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Teleop.cargoShip -> {
                if (isOnOpponentField) errors.inc("ERROR outtake while on opponent field")
                when (currentGamePiece) {
                    GamePieces.Cargo -> gamepieces.inc("Tele, Cargo, Cargo ship")
                    GamePieces.Hatch -> gamepieces.inc("Tele, Hatch, Cargo ship")
                    GamePieces.None -> errors.inc("ERROR outtake no gamepiece")
                }
            }
            Teleop.opponentField -> {
                isOnOpponentField = !isOnOpponentField
                if (isOnOpponentField) {
                    lastOpponentFieldTime = it.time
                } else {
                    defence.inc("Defending count")
                    defence.plus("Defending time", it.time - lastOpponentFieldTime)
                }
            }
            Teleop.defended -> {
                isDefended = !isDefended
                if (isDefended) {
                    lastDefendedTime = it.time
                } else {
                    defence.inc("Defended count")
                    defence.plus("Defended time", it.time - lastDefendedTime)
                }
            }
        }
    }
    if (isDefended) {
        defence.inc("Defended count")
        defence.plus("Defended time", 150 - lastDefendedTime)
    }

    val actions5 = (1 until 5).map{"Action $it" to ""}.toMutableList()
    actions5.forEachIndexed{ i, it ->
        if (i+1 <= autonActions.size) actions5[i] = it.first to autonActions[i]
    }

    return defining + gamepieces.toSortedMap() + actions5 + defence + climb + errors + misc
}

fun main() {
    val a = listOf(
            mapOf("one" to 1, "too" to 1),
            mapOf("one" to 2, "two" to 2)
    )

    println(dataFrameOf(a))
}