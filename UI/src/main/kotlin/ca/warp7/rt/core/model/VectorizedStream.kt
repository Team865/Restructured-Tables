package ca.warp7.rt.core.model

interface VectorizedStream {
    fun mapCols(vararg columns: Pair<String, (PipelineExpression) -> Any?>)
}