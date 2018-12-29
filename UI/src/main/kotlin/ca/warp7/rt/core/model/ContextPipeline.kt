package ca.warp7.rt.core.model


interface ContextPipeline {
    fun fetch()
    val virtualContext: Context
    fun streamOf(metrics: Set<Metric<*>>): Stream

    interface Stream {
        fun mapCols(vararg columns: Pair<String, (PipelineExpression) -> Any?>)
    }
}