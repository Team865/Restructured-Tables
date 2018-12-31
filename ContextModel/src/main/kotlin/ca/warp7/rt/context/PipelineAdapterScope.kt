package ca.warp7.rt.context

interface PipelineAdapterScope {
    fun stream(metrics: MetricsSet): PipelineMetricStream
}