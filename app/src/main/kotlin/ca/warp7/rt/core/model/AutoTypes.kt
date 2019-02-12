@file:Suppress("unused")

package ca.warp7.rt.core.model

import ca.warp7.rt.api.*

val autoTypes: ContextAdapter = {
    stream(metrics = teamNumber_ / scout_ / board_ / match_).mapPure(
            "start_position" to { it.data["Start position"] },
            "auto_scale" to { it.data.count("Auto scale success") },
            "auto_switch" to { it.data.count("Auto switch success") }
    ).mapFrom(metrics = matchNumber_ / compLevel_).with(
            "switch_plate" to {
                when (lookup(it.matchNumber)["game_data", "???"][0]) {
                    'L' -> "Left"
                    'R' -> "Right"
                    else -> ""
                }
            },
            "scale_plate" to {
                when (lookup(it.matchNumber)["game_data", "???"][1]) {
                    'L' -> "Left"
                    'R' -> "Right"
                    else -> ""
                }
            }
    ).mapLast(
            "auto_type" to {
                val startPosition = it["start_position"]
                if (it["auto_line", 0] > 0 && startPosition != "None") when {
                    it["auto_scale", 0] > 0 -> when (startPosition) {
                        "Center" -> "Center Scale"
                        it["scale_plate"] -> "Near Scale"
                        else -> "Far Scale"
                    }
                    it["auto_switch", 0] > 0 -> when (startPosition) {
                        "Center" -> "Center Switch"
                        else -> "Side Switch"
                    }
                    else -> "Auto Line"
                } else "None"
            }
    )
}