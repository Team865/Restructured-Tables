package ca.warp7.rt.context

interface PipelineMappingScope {
    fun lookup(vararg metrics: AnyMetric): Map<String, Any?>
}