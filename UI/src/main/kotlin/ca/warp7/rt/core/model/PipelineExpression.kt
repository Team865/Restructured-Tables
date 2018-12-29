package ca.warp7.rt.core.model

interface PipelineExpression : Map<String, Any?> {
    val data: Map<String, Any?>
    fun getMetric(metric: Metric<*>): Any
}