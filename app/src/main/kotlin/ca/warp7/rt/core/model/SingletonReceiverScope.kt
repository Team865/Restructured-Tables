package ca.warp7.rt.core.model

import ca.warp7.rt.api.MetricsSet
import ca.warp7.rt.api.PipelineMetricStream
import ca.warp7.rt.api.PipelineReceiverScope
import ca.warp7.rt.api.div

@Suppress("unused")
internal class SingletonReceiverScope : PipelineReceiverScope {
    override val availableData: Array<MetricsSet>
        get() = arrayOf(teamNumber_ / scout_ / board_ / match_)

    override fun stream(metrics: MetricsSet): PipelineMetricStream {
        TODO("not implemented")
    }
}