package ca.warp7.rt.context

interface PipelineVector : Map<String, Any?> {
    val data: Map<String, Any?>
    fun <T> getMetric(metric: Metric<T>): Metric<T>
}