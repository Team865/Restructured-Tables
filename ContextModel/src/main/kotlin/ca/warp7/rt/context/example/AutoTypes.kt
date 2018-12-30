package ca.warp7.rt.context.example

import ca.warp7.rt.context.*


class AutoTypes : ContextAdapter1 {

    private val entryMetrics: MetricsSet = teamNumber_ /
            scout_ / driverStation_ /
            matchNumber_ / compLevel_ /
            event_ / year_ / dataSource_

    override fun update(contextReference: ContextReference, pipeline: ContextPipeline) {
        pipeline.addAdapter("Auto Types") {
            stream(entryMetrics).mapCols(
                    "start_position" to {
                        it.data["Start position"]
                    },
                    "switch_plate" to {
                        when (contextReference.lookup(it.matchNumber)["game_data", "???"][0]) {
                            'L' -> "Left"
                            'R' -> "Right"
                            else -> ""
                        }
                    },
                    "scale_plate" to {
                        when (contextReference.lookup(it.matchNumber)["game_data", "???"][1]) {
                            'L' -> "Left"
                            'R' -> "Right"
                            else -> ""
                        }
                    },
                    "auto_scale" to { it.data.count("Auto scale success") },
                    "auto_switch" to { it.data.count("Auto switch success") },
                    "auto_type" to {
                        val startPosition = it["start_position"]
                        if (it.int("auto_line") > 0 && startPosition != "None") when {
                            it.int("auto_scale") > 0 -> when (startPosition) {
                                "Center" -> "Center Scale"
                                it["scale_plate"] -> "Near Scale"
                                else -> "Far Scale"
                            }
                            it.int("auto_switch") > 0 -> when (startPosition) {
                                "Center" -> "Center Switch"
                                else -> "Side Switch"
                            }
                            else -> "Auto Line"
                        } else "None"
                    }
            )
        }
    }
}