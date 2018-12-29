package ca.warp7.rt.context.example

import ca.warp7.rt.context.*


class AutoTypes : ContextAdapter {

    private val metricsSet: MetricsSet = teamNumber_ /
            scout_ / driverStation_ /
            matchNumber_ / compLevel_ /
            event_ / year_ / dataSource_

    override fun update(contextReference: ContextReference, pipeline: ContextPipeline) {
        pipeline.streamOf(metricsSet).mapCols(
                "start_position" to {
                    it.data["Start position"]
                },
                "switch_plate" to {
                    if (contextReference.lookup(it.matchNumber).str("game_data")[0] == 'L') "Left" else "Right"
                },
                "scale_plate" to {
                    if (contextReference.lookup(it.matchNumber).str("game_data")[1] == 'L') "Left" else "Right"
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