package ca.warp7.rt.core.model


interface ContextPipeline {
    fun fetch()
    val virtualContext: Context
    fun streamOf(metrics: Set<Metric<*>>): Stream

    interface Stream {
        fun mapCols(vararg columns: Pair<String, PipelineIndex.() -> Any>)
    }
}


interface PipelineIndex {
    val data: MutableMap<String, Any?>
    fun getMetric(metric: Metric<*>)
}


val PipelineIndex.matchNumber get() = this.getMetric(matchNumber_)
internal infix fun String.to(that: PipelineIndex.() -> Unit): Pair<String, PipelineIndex.() -> Unit> = Pair(this, that)