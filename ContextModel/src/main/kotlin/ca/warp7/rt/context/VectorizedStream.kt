package ca.warp7.rt.context

interface VectorizedStream {
    fun mapCols(vararg columns: Pair<String, (PipelineExpression) -> Any?>)
}