package ca.warp7.rt.api

interface PipelineReceiverScope {
    val availableData: Array<MetricsSet>
    fun stream(metrics: MetricsSet): PipelineMetricStream
}