package ca.warp7.rt.context

interface PipelineStream {
    fun mapCols(vararg columns: Pair<String, (PipelineVector) -> Any?>) = this
}