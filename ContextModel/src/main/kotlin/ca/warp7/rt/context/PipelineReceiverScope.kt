package ca.warp7.rt.context

interface PipelineReceiverScope {
    fun stream(metrics: MetricsSet): PipelineMetricStream
}