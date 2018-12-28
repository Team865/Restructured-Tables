package ca.warp7.rt.core.model


class AutoTypes : ContextAdapter {

    override fun update(pipeline: ContextPipeline) {
        pipeline.streamOf(Entry.metricsSet).mapCols(
                "t" to {}
        )
        /*"start_position" to {
            it.data.series["Start position"].finalStr
        },
        "switch_plate" to {
            if (lookup(it.match)["game_data"][0] == 'L') "Left" else "Right"
        },
        "scale_plate" to {
            if (lookup(it.match)["game_data"][1] == 'L') "Left" else "Right"
        },
        "auto_scale" to { it.data.series["Auto scale success"].count },
        "auto_switch" to { it.data.series["Auto switch success"].count },
        "auto_type" to {
            val startPosition = it["start_position"]
            if (it["auto_line"] > 0 && startPosition != "None") when {
                it["auto_scale"] > 0 -> when (startPosition) {
                    "Center" -> "Center Scale"
                    scalePlate -> "Near Scale"
                    else -> "Far Scale"
                }
                it["auto_switch"] > 0 -> when (startPosition) {
                    "Center" -> "Center Switch"
                    else -> "Side Switch"
                }
                else -> "Auto Line"
            } else "None"
        }*/

    }
}