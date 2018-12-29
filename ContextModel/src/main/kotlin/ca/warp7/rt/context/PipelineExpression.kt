package ca.warp7.rt.context

interface PipelineExpression : Map<String, Any?> {
    val data: Map<String, Any?>
    fun getMetric(metric: Metric<*>): Any
}