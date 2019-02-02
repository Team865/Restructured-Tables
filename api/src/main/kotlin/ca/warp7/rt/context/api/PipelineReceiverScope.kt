package ca.warp7.rt.context.api

interface PipelineReceiverScope {
    val availableData: Array<MetricsSet>
    fun stream(metrics: MetricsSet): PipelineMetricStream
}