package ca.warp7.rt.api

interface PipelineMappingScope {
    fun lookup(vararg metrics: AnyMetric): Map<String, Any?>
}