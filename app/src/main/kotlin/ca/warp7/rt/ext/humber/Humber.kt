@file:Suppress("unused")

package ca.warp7.rt.ext.humber

import ca.warp7.android.scouting.v5.boardfile.exampleBoardfile
import ca.warp7.android.scouting.v5.entry.V5Entry
import ca.warp7.rt.ext.scanner.DecodedEntry
import krangl.DataFrame
import krangl.dataFrameOf
import java.io.File

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

@Suppress("unused")
fun process(data: List<V5Entry>): DataFrame {
    val rows = mutableListOf<Map<String, Any>>()
    for (entry in data) {
    }
    return dataFrameOf(rows)
}