package ca.warp7.rt.context.api

interface PipelineMappingScope {
    fun lookup(vararg metrics: AnyMetric): Map<String, Any?>
}