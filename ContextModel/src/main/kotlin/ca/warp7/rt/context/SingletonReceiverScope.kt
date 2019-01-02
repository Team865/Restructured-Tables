package ca.warp7.rt.context

@Suppress("unused")
internal class SingletonReceiverScope : PipelineReceiverScope {
    override val availableData: List<MetricsSet>
        get() = TODO("not implemented")

    override fun stream(metrics: MetricsSet): PipelineMetricStream {
        TODO("not implemented")
    }
}