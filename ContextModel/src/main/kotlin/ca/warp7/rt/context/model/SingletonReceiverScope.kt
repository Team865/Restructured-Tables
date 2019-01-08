package ca.warp7.rt.context.model

import ca.warp7.rt.context.api.MetricsSet
import ca.warp7.rt.context.api.PipelineMetricStream
import ca.warp7.rt.context.api.PipelineReceiverScope
import ca.warp7.rt.context.api.div

@Suppress("unused")
internal class SingletonReceiverScope : PipelineReceiverScope {
    override val availableData: Array<MetricsSet>
        get() = arrayOf(teamNumber_ / scout_ / board_ / match_)

    override fun stream(metrics: MetricsSet): PipelineMetricStream {
        TODO("not implemented")
    }
}