package ca.warp7.rt.context

interface PipelineMapScope {
    fun lookup(vararg metrics: AnyMetric): Map<String, Any?>
}