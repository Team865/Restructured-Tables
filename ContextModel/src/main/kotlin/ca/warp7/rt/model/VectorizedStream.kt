package ca.warp7.rt.model

interface VectorizedStream {
    fun mapCols(vararg columns: Pair<String, (PipelineExpression) -> Any?>)
}