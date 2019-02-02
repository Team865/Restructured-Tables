package ca.warp7.rt.context.api

interface PipelineAdapterScope {
    fun stream(metrics: MetricsSet): PipelineMetricStream
}