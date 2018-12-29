package ca.warp7.rt.context

interface PipelineVector : Map<String, Any?> {
    val data: Map<String, Any?>
    fun getMetric(metric: Metric<*>): Any
}