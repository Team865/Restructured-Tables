package ca.warp7.rt.context

interface PipelineReceiverScope {
    val availableData: List<MetricsSet>
    fun stream(metrics: MetricsSet): PipelineMetricStream
}