package ca.warp7.rt.core.model


interface ContextPipeline {
    fun fetch()
    fun streamOf(metrics: Set<Metric<*>>): VectorizedStream
}