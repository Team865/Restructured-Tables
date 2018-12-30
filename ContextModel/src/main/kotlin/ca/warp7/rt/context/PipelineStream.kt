package ca.warp7.rt.context

interface PipelineStream {
    fun mapPure(vararg columns: Pair<String, PipelineMapScope.(PipelineVector) -> Any?>) = this
    fun mapDependent(
            vararg columns: Pair<String, PipelineMapScope.(PipelineVector) -> Any?>, dependentMetrics: MetricsSet? = null) = this
}