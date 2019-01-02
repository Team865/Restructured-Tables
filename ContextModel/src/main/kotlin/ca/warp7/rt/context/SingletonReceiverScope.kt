package ca.warp7.rt.context

@Suppress("unused")
internal class SingletonReceiverScope : PipelineReceiverScope {
    override val availableData: Array<MetricsSet>
        get() = arrayOf(teamNumber_ / scout_ / board_ / match_)

    override fun stream(metrics: MetricsSet): PipelineMetricStream {
        TODO("not implemented")
    }
}