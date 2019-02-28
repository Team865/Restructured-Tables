package ca.warp7.rt.api

interface PipelineAdapterScope {
    fun stream(metrics: MetricsSet): PipelineMetricStream
}