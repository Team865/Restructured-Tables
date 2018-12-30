package ca.warp7.rt.context

interface PipelineStream {
    fun mapPure(vararg mappedColumns: MappedColumn) = this
    fun mapFrom(stream: MetricsSet? = null): PipelineMapInvoker
    fun mapLast(vararg mappedColumns: MappedColumn)
}