package ca.warp7.rt.context

interface PipelineScope {
    fun stream(metrics: MetricsSet): PipelineStream
}