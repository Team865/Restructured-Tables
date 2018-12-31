package ca.warp7.rt.context

interface PipelineStreamVector : Map<String, Any?> {
    val data: Map<String, Any?>
    fun <T> getMetric(metric: BaseMetric<T>): BaseMetric<T>
}